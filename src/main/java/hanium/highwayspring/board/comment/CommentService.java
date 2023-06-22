package hanium.highwayspring.board.comment;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.BoardRepository;
import hanium.highwayspring.board.comment.repository.CommentRepository;
import hanium.highwayspring.config.res.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    public ResponseDTO<?> createComment(CommentRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow();
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
                .user(requestDto.getUserId())
                .board(board)
                .content(requestDto.getContent())
                .isDeleted(false)
                .build();
        if (parent != null)
            comment.updateParent(parent);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = null;
        if (parent != null) {
            commentResponseDto = CommentResponseDto.builder()
                    .id(comment.getId())
                    .userId(comment.getUser().getUid())
                    .content(comment.getContent())
                    .isDeleted(comment.getIsDeleted())
                    .createDate(comment.getCreateDate())
                    .modifiedDate(comment.getModifiedDate())
                    .parentId(comment.getParent().getId())
                    .build();
        } else {
            commentResponseDto = CommentResponseDto.builder()
                    .id(comment.getId())
                    .userId(comment.getUser().getUid())
                    .content(comment.getContent())
                    .isDeleted(comment.getIsDeleted())
                    .createDate(comment.getCreateDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        }
        return ResponseDTO.success(commentResponseDto);
    }

    @Transactional(readOnly = true)
    public ResponseDTO<?> getAllCommentsByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
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

    @Transactional
    public ResponseDTO<?> updateComment(CommentRequestDto requestDto){
        Optional<Comment> comment = commentRepository.findById(requestDto.getId());
        comment.get().update(requestDto);
        CommentResponseDto commentResponseDto = null;
        if (requestDto.getParentId() != null) {
            commentResponseDto = CommentResponseDto.builder()
                    .id(comment.get().getId())
                    .userId(comment.get().getUser().getUid())
                    .content(comment.get().getContent())
                    .createDate(comment.get().getCreateDate())
                    .modifiedDate(comment.get().getModifiedDate())
                    .parentId(comment.get().getParent().getId())
                    .build();
        } else {
            commentResponseDto = CommentResponseDto.builder()
                    .id(comment.get().getId())
                    .userId(comment.get().getUser().getUid())
                    .content(comment.get().getContent())
                    .createDate(comment.get().getCreateDate())
                    .modifiedDate(comment.get().getModifiedDate())
                    .build();
        }
        return ResponseDTO.success(commentResponseDto);
    }

    @Transactional
    public ResponseDTO<?> deleteComment(Long commentId){
        Comment comment = commentRepository.findCommentByIdWithParent(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
        if(comment.getChildren().size() != 0){
            comment.changeIsDeleted(true);
        }else{
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
        return ResponseDTO.success("");
    }

    private Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if(parent != null && parent.getIsDeleted() && parent.getChildren().size() == 1)
            return getDeletableAncestorComment(parent);
        return comment;
    }
}