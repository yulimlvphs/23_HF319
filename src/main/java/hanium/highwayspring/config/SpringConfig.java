package hanium.highwayspring.config;

import hanium.highwayspring.board.heart.HeartRepository;
import hanium.highwayspring.board.heart.HeartService;
import hanium.highwayspring.dept.DeptRepository;
import hanium.highwayspring.tag.TagRepository;
import hanium.highwayspring.user.auth.AuthRepository;
import hanium.highwayspring.board.repository.BoardRepository;
import hanium.highwayspring.board.BoardService;
import hanium.highwayspring.board.comment.repository.CommentRepository;
import hanium.highwayspring.board.comment.CommentService;
import hanium.highwayspring.config.jwt.JwtTokenProvider;
import hanium.highwayspring.school.repository.SchoolRepository;
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
    private final TagRepository tagRepository;
    private final DeptRepository deptRepository;

    public SpringConfig(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthRepository authRepository, SchoolRepository schoolRepository, BoardRepository boardRepository, HeartRepository heartRepository, CommentRepository commentRepository, TagRepository tagRepository, DeptRepository deptRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
        this.schoolRepository = schoolRepository;
        this.boardRepository = boardRepository;
        this.heartRepository = heartRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
        this.deptRepository = deptRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(jwtTokenProvider, userRepository, passwordEncoder, authRepository, schoolRepository, tagRepository);
    }

    @Bean
    public SchoolService schoolService(){
        return new SchoolService(schoolRepository ,tagRepository, deptRepository);
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