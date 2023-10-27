package hanium.highwayspring.user;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.config.res.TokenResponse;
import hanium.highwayspring.config.res.UserRequest;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.repository.SchoolRepository;
import hanium.highwayspring.tag.TagDTO;
import hanium.highwayspring.tag.TagRepository;
import hanium.highwayspring.user.auth.Auth;
import hanium.highwayspring.user.auth.AuthRepository;
import hanium.highwayspring.config.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final SchoolRepository schoolRepository;
    private final TagRepository tagRepository;

    public UserService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder1, AuthRepository authRepository, SchoolRepository schoolRepository, TagRepository tagRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder1;
        this.authRepository = authRepository;
        this.schoolRepository = schoolRepository;
        this.tagRepository = tagRepository;
    }

    public TokenResponse register(@RequestBody User u) {
        log.info(u.getUid());
        User user = User.builder()
                .uid(u.getUid())
                .pwd(passwordEncoder.encode(u.getPwd()))
                .name(u.getName())
                .email(u.getEmail())
                .gender(u.getGender())
                .age(u.getAge())
                .role(u.getRole())
                .schoolId(u.getSchoolId())
                .build();
        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getUid());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUid());
        Auth auth = Auth.builder()
                .user(user)
                .point(0L)
                .refreshToken(refreshToken)
                .build();
        authRepository.save(auth);
        //토큰들을 반환한 순간 로그인 처리가 된 것임
        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .ACCESS_TOKEN_EXPIRATION(jwtTokenProvider.getTokenExpiration(accessToken))
                .build();
    }

    @Transactional
    public TokenResponse doLogin(@RequestBody UserRequest userRequest) throws Exception { //토큰 발급과 갱신을 수행
        User user = userRepository.findByUid(userRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Auth auth = authRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
        if (!passwordEncoder.matches(userRequest.getUserPw(), user.getPwd())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = "";
        String refreshToken = auth.getRefreshToken();   //DB에서 가져온 Refresh 토큰

        //refresh 토큰은 유효 할 경우
        //사실 이것도 로그인을 하는것이라면 refresh 토큰이 유효한지 파악안하고 그냥 둘다 재발급 하는게 나을듯?
        if (jwtTokenProvider.isValidRefreshToken(refreshToken)) { //refreshToken의 유효성을 확인
            accessToken = jwtTokenProvider.createAccessToken(user.getUid()); //Access Token 새로 만들어서 줌
            return TokenResponse.builder()
                    .ACCESS_TOKEN(accessToken)
                    .REFRESH_TOKEN(refreshToken)
                    .ACCESS_TOKEN_EXPIRATION(jwtTokenProvider.getTokenExpiration(accessToken))
                    .build();
        } else {
            //둘 다 새로 발급
            accessToken = jwtTokenProvider.createAccessToken(user.getUid());
            refreshToken = jwtTokenProvider.createRefreshToken(user.getUid());
            auth.refreshUpdate(refreshToken);   //DB Refresh 토큰 갱신
        }

        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .ACCESS_TOKEN_EXPIRATION(jwtTokenProvider.getTokenExpiration(accessToken))
                .build();
    }

    public TokenResponse issueAccessToken(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        //accessToken이 만료됐고 refreshToken이 맞으면 accessToken을 새로 발급(refreshToken의 내용을 통해서)
        if (!jwtTokenProvider.isValidAccessToken(accessToken)) {  //클라이언트에서 토큰 재발급 api로의 요청을 확정해주면 이 조건문은 필요없다.
            System.out.println("Access 토큰 만료됨");
            if (jwtTokenProvider.isValidRefreshToken(refreshToken)) {     //들어온 Refresh 토큰이 유효한지
                System.out.println("Refresh 토큰은 유효함");
                Claims claimsToken = jwtTokenProvider.getClaimsToken(refreshToken);
                String userId = (String) claimsToken.get("userId");
                Optional<User> user = userRepository.findByUid(userId);
                String tokenFromDB = authRepository.findByUserId(user.get().getId()).get().getRefreshToken();
                System.out.println("tokenFromDB = " + tokenFromDB);
                if (refreshToken.equals(tokenFromDB)) {   //DB의 refresh토큰과 지금들어온 토큰이 같은지 확인
                    System.out.println("Access 토큰 재발급 완료");
                    accessToken = jwtTokenProvider.createAccessToken(userId);
                } else {
                    //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                    System.out.println("Refresh Token Tampered");
                    //예외발생
                }
            } else {
                //입력으로 들어온 Refresh 토큰이 유효하지 않음
            }
        }
        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .ACCESS_TOKEN_EXPIRATION(jwtTokenProvider.getTokenExpiration(accessToken))
                .build();
    }

    public ResponseDTO<?> findByToken(HttpServletRequest request) {
        return ResponseDTO.success(getUserInfo(request));
    }

    public UserDTO getUserInfo(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        Claims claimsFormToken = jwtTokenProvider.getClaimsFormToken(accessToken);
        String userId = (String) claimsFormToken.get("userId");

        //User 정보 조회 / 저장
        User user = userRepository.findByUid(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        UserDTO userDTO = UserDTO.toEntity(user); //조회한 user 정보 저장/반환

        Auth auth = authRepository.findByUserId(user.getId()).orElseThrow();
        userDTO.setPoint(auth.getPoint()); //user point 저장/반환

        // 학교관련 조회
        Optional<School> schoolOptional = Optional.ofNullable(user.getSchoolId()) //user의 SchoolId가 null이 아니라면 학교를 조회
                .map(schoolId -> schoolRepository.findById(schoolId.getId()).orElse(null));
        userDTO.setSchoolName(schoolOptional.map(School::getSchoolName).orElse(null));
        userDTO.setSchoolId(schoolOptional.map(School::getId).orElse(null));

        //사용자가 지정한 학교에 대한 태그를 반환
        List<TagDTO> tags = schoolOptional.map(school -> tagRepository.findNameAndCodeById(school.getId()))
                .orElse(Collections.emptyList());
        userDTO.setTag(tags);

        return userDTO;
    }

    public Optional<User> getUser(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        if (accessToken == null) {
            User user = User.builder()
                    .id(0L)
                    .uid("guest")
                    .build();
            return Optional.ofNullable(user);
        } else {
            Claims claimsFormToken = jwtTokenProvider.getClaimsFormToken(accessToken);
            String userId = (String) claimsFormToken.get("userId");
            return userRepository.findByUid(userId);
        }
    }

    public User findByUid(String uid) {
        return userRepository.findByUid(uid).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
    }

    public Boolean idCheck(String id) {
        Optional<User> user = userRepository.findByUid(id);
        if (user.isEmpty() && id.length() > 0)
            return true;
        else
            return false;
    }

    @Transactional
    public UserDTO updatePoint(String userId, Long point) {
        User user = userRepository.findByUid(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Auth auth = authRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
        if (auth.getPoint() != null)
            auth.updatePoint(point + auth.getPoint());
        else
            auth.updatePoint(point);
        UserDTO dto = UserDTO.toEntity(user);
        dto.setPoint(auth.getPoint());
        return dto;
    }
}
