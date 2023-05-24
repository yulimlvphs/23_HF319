package hanium.highwayspring.config.res;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class TokenResponse {
    private String ACCESS_TOKEN;
    private String REFRESH_TOKEN;
    private Date ACCESS_TOKEN_EXPIRATION;
}
