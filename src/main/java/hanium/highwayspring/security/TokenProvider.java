package hanium.highwayspring.security;

import hanium.highwayspring.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "NMA8JPctFuna59f5ALhj1Js2Hsd45Gso0aNks25iuGh9sPklMn0sn8Hbs7V4a5wAsdGWs7qg96xgsSa2g1swQss32gmcXas";

    public String create(User userEntity) {
        // 기한은 지금부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));
        /*
         *    ---- 토큰 구조 ----
         *  { // header
         *  	"alg" : "HS512"
         *  },
         *  { // payload
         *  	"sub" : "40288093784915d201784916a40c0001",
         *  	"iss" : "demo app",
         *  	"iat" : 1595733657,
         *  	"exp" : 1596597657
         *  }.
         *  // SECRET_KEY를 이용해 서명한 부분
         *  Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQ.....
         */

        // JWT Token 생성
        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)//기존에 .signWith에서 오류나길래 이걸로 바꿨어
                // payload에 들어갈 내용
                .claim("user_no", userEntity.getId())
                .claim("user_id", userEntity.getUid())
                .claim("user_name", userEntity.getName())
                .claim("user_email", userEntity.getEmail())
                .claim("user_gender", userEntity.getGender())
                .claim("user_age", userEntity.getAge())
                .claim("user_role", userEntity.getRole())
                .setSubject(userEntity.getUid()) // sub
                .setIssuer("demo app")            // iss
                .setIssuedAt(new Date())        // iat
                .setExpiration(expiryDate)        // exp
                .compact();
    }

    public String validateAndGetUserId(String token) {
        // parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 우리는 userId가 필요하므로 getBody를 부른다
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // subject 즉 사용자 아이디를 리턴한다.
    }

    /**
     * 토큰의 Claim 디코딩
     */
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
