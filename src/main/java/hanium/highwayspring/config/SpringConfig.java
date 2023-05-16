package hanium.highwayspring.config;

import hanium.highwayspring.jwt.AuthRepository;
import hanium.highwayspring.jwt.JwtTokenProvider;
import hanium.highwayspring.user.UserRepository;
import hanium.highwayspring.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public SpringConfig(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthRepository authRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(jwtTokenProvider, userRepository, passwordEncoder, authRepository);
    }
}
