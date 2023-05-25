package hanium.highwayspring.comment;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRespository commentRespository;
    public ResponseDTO<?> createComment(CommentRequestDto requestDto, HttpServletRequest request){
        return null;
    }
}