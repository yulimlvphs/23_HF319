package hanium.highwayspring.config;

import hanium.highwayspring.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SpringSecurity(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http              // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정
                .csrf()                    // csrf는 현재 사용하지 않으므로 disable
                .disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .httpBasic()            // token을 사용하므로 basic 인증 disable
                .disable()
                .sessionManagement()    // session 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()    // /와 /autho/** 경로는 인증 안해도 됨
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // - (1)
                .antMatchers("/", "/user/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest()            // /와 /auth/** 이외의 모든 경로는 인증 해야 됨
                .authenticated()
                .and()
                .cors().and();

        // filter 등록
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행한다.
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // - (3)
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*@Override //스프링 시큐리티 5.7 이상 버전으로 인해 수정
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().disable()
//                .csrf().disable()
//                .formLogin().disable()
//                .headers().frameOptions().disable();
        // http 시큐리티 빌더
        http.cors().disable()                // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정
                .csrf()                    // csrf는 현재 사용하지 않으므로 disable
                .disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .httpBasic()            // token을 사용하므로 basic 인증 disable
                .disable()
                .sessionManagement()    // session 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()    // /와 /autho/** 경로는 인증 안해도 됨
                .antMatchers("/", "/user/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest()            // /와 /auth/** 이외의 모든 경로는 인증 해야 됨
                .authenticated();

        // filter 등록
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행한다.
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
