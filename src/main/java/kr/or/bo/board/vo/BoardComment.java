package kr.or.bo.board.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardComment {
	private int boardCommentNo;
	private String boardCommentWriter;
	private String boardCommentContent;
	private String boardCommentdate;
	private int boardRef;
	private int boardCommentRef;
	
	
}
