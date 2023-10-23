package hanium.highwayspring.config;

import hanium.highwayspring.config.jwt.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
                .excludePathPatterns("/user/*")
                .excludePathPatterns("/school/*")
                .excludePathPatterns("/**/list/*")
                .excludePathPatterns("/feedback/list")
                .excludePathPatterns("/**/detail/*");

    }
}