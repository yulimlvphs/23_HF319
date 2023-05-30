package hanium.highwayspring.comment;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.BoardRespository;
import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final BoardRespository boardRespository;
    private final CommentRespository commentRespository;
    public ResponseDTO<?> createComment(CommentRequestDto requestDto){
        Board board = boardRespository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if(board == null)
            return ResponseDTO.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        Comment parent = null;
        if(requestDto.getParentId() != null){
            parent = commentRespository.findById(requestDto.getParentId()).orElseThrow();
            if(parent == null)
                return  ResponseDTO.fail("NOT_FOUND", "부모댓글이 존재하지 않습니다.");

            if(parent.getBoard().getId() != requestDto.getBoardId())
                return ResponseDTO.fail("NOT_FOUND", "부모댓글과 자식댓글의 게시글 번호가 일치하지 않습니다.");

        }
        Comment comment = Comment.builder()
                .userId(requestDto.getUserId())
                .board(board)
                .content(requestDto.getContent())
                .build();
        if(parent != null)
            comment.updateParent(parent);
        commentRespository.save(comment);
        CommentResponseDto commentResponseDto = null;
        if(parent != null){
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
}