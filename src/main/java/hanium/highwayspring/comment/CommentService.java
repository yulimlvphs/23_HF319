package hanium.highwayspring.comment;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.BoardRepository;
import hanium.highwayspring.comment.repository.CommentRepository;
import hanium.highwayspring.config.res.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRespository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRespository) {
        this.commentRepository = commentRepository;
        this.boardRespository = boardRespository;
    }

    public ResponseDTO<?> createComment(CommentRequestDto requestDto) {
        Board board = boardRespository.findById(requestDto.getBoardId()).orElseThrow();
        if (board == null)
            return ResponseDTO.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        Comment parent = null;
        if (requestDto.getParentId() != null) {
            parent = commentRepository.findById(requestDto.getParentId()).orElseThrow();
            if (parent == null)
                return ResponseDTO.fail("NOT_FOUND", "부모댓글이 존재하지 않습니다.");

            if (parent.getBoard().getId() != requestDto.getBoardId())
                return ResponseDTO.fail("NOT_FOUND", "부모댓글과 자식댓글의 게시글 번호가 일치하지 않습니다.");

        }
        Comment comment = Comment.builder()
                .userId(requestDto.getUserId())
                .board(board)
                .content(requestDto.getContent())
                .build();
        if (parent != null)
            comment.updateParent(parent);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = null;
        if (parent != null) {
            commentResponseDto = CommentResponseDto.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .content(comment.getContent())
                    .createDate(comment.getCreateDate())
                    .modifiedDate(comment.getModifiedDate())
                    .parentId(comment.getParent().getId())
                    .build();
        } else {
            commentResponseDto = CommentResponseDto.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .content(comment.getContent())
                    .createDate(comment.getCreateDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        }
        return ResponseDTO.success(commentResponseDto);
    }

    @Transactional(readOnly = true)
    public ResponseDTO<?> getAllCommentsByBoard(Long boardId) {
        Board board = boardRespository.findById(boardId).orElseThrow();
        if (board == null)
            return ResponseDTO.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");

        List<Comment> commentList = commentRepository.findAllByBoard(board);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> map = new HashMap<>();

        commentList.stream().forEach(c -> {
                    CommentResponseDto cdto = new CommentResponseDto(c);
                    if (c.getParent() != null) {
                        cdto.setParentId(c.getParent().getId());
                    }
                    map.put(cdto.getId(), cdto);
                    if (c.getParent() != null) map.get(c.getParent().getId()).getChildren().add(cdto);
                    else commentResponseDtoList.add(cdto);
                }
        );
        return ResponseDTO.success(commentResponseDtoList);
    }
}