package com.hai.quizapp.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hai.quizapp.dtos.RefreshTokenRequest;
import com.hai.quizapp.dtos.users.LoginRequest;
import com.hai.quizapp.dtos.users.LoginResponse;
import com.hai.quizapp.dtos.users.RegisterRequest;
import com.hai.quizapp.entities.Role;
import com.hai.quizapp.entities.User;
import com.hai.quizapp.enums.RoleEnum;
import com.hai.quizapp.exceptions.DuplicateResourceException;
import com.hai.quizapp.exceptions.UnauthorizedException;
import com.hai.quizapp.repositories.RoleRepository;
import com.hai.quizapp.repositories.UserRepository;
import com.hai.quizapp.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

/**
 * Service for authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Authenticate user and generate tokens.
     */
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        logger.info("Login attempt for user: {}", request.email());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new UnauthorizedException("User not found"));

            Set<String> roles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet());

            String accessToken = tokenService.generateToken(user, roles);

            // Still use JwtUtil for refresh token generation
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                    user.getEmail(),
                    user.getFullName(),
                    roles
            );

            logger.info("Login successful for user: {}", request.email());

            return LoginResponse.of(
                    accessToken,
                    refreshToken,
                    jwtUtil.getAccessTokenExpiration(),
                    userInfo
            );

        } catch (BadCredentialsException e) {
            logger.error("Login failed for user: {}", request.email());
            throw new UnauthorizedException("Invalid email or password");
        }
    }

    /**
     * Register new user.
     */
    @Transactional
    public LoginResponse register(RegisterRequest request) {
        logger.info("Registration attempt for user: {}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Get or create default USER role
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseGet(() -> {
                    Role newRole = Role.builder()
                            .name(RoleEnum.ROLE_USER)
                            .description("Default user role")
                            .build();
                    return roleRepository.save(newRole);
                });

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .active(true)
                .roles(roles)
                .build();

        userRepository.save(user);

        logger.info("User registered successfully: {}", request.email());

        // Auto login after registration
        LoginRequest loginRequest = new LoginRequest(request.email(), request.password());
        return login(loginRequest);
    }

    /**
     * Refresh access token using refresh token.
     */
    @Transactional(readOnly = true)
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        logger.info("Token refresh attempt");

        String refreshToken = request.refreshToken();

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtUtil.validateToken(refreshToken, userDetails)) {
            throw new UnauthorizedException("Refresh token is expired or invalid");
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        String newAccessToken = tokenService.generateToken(user, roles);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                user.getEmail(),
                user.getFullName(),
                roles
        );

        logger.info("Token refreshed successfully for user: {}", username);

        return LoginResponse.of(
                newAccessToken,
                newRefreshToken,
                jwtUtil.getAccessTokenExpiration(),
                userInfo
        );
    }
}
