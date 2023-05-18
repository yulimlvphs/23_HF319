package hanium.highwayspring.jwt;

import hanium.highwayspring.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        System.out.println("JwtToken 호출");
        String accessToken = request.getHeader("ACCESS_TOKEN");
        System.out.println("AccessToken:" + accessToken);
        String refreshToken = request.getHeader("REFRESH_TOKEN");
        System.out.println("RefreshToken:" + refreshToken);

        if (accessToken != null && jwtTokenProvider.isValidAccessToken(accessToken)) {
            response.setHeader("ACCESS_TOKEN_EXPIRATION", jwtTokenProvider.getTokenExpiration(accessToken).toString());
            return true;
        }

        response.setStatus(401);
        response.setHeader("ACCESS_TOKEN", accessToken);
        response.setHeader("REFRESH_TOKEN", refreshToken);
        response.setHeader("msg", "Check the tokens.");
        return false;
    }
}
