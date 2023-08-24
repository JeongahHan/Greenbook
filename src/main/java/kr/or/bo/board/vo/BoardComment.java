package kr.or.bo.board.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardComment {
	private int boardCommentNo; //댓글번호
	private String boardCommentWriter; //작성자
	private String boardCommentContent; //댓글내용
	private String boardCommentdate;  //작성일
	private int boardRef; //게시글 번호
	private int boardCommentRef; //댓글 참조 번호(댓글에다가 또 댓글을 달았을때 그 번호)
	
	private int isLike; //해당하는 회원이 좋아요를 눌렀는지
	private int likeCount; //좋아요 총 갯수
	
}
