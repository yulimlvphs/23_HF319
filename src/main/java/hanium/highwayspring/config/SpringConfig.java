package hanium.highwayspring.config;

import hanium.highwayspring.board.heart.HeartRepository;
import hanium.highwayspring.board.heart.HeartService;
import hanium.highwayspring.user.auth.AuthRepository;
import hanium.highwayspring.board.repository.BoardRepository;
import hanium.highwayspring.board.BoardService;
import hanium.highwayspring.board.comment.repository.CommentRepository;
import hanium.highwayspring.board.comment.CommentService;
import hanium.highwayspring.config.jwt.JwtTokenProvider;
import hanium.highwayspring.school.SchoolRepository;
import hanium.highwayspring.school.SchoolService;
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
    private final SchoolRepository schoolRepository;
    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;

    public SpringConfig(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthRepository authRepository, SchoolRepository schoolRepository, BoardRepository boardRepository, HeartRepository heartRepository, CommentRepository commentRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
        this.schoolRepository = schoolRepository;
        this.boardRepository = boardRepository;
        this.heartRepository = heartRepository;
        this.commentRepository = commentRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(jwtTokenProvider, userRepository, passwordEncoder, authRepository, schoolRepository);
    }

    @Bean
    public SchoolService schoolService(){
        return new SchoolService(schoolRepository);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository);
    }

    @Bean
    public HeartService heartService(){
        return new HeartService(heartRepository);
    }

    @Bean
    public CommentService commentService() {
        return new CommentService(commentRepository, boardRepository);
    }

}