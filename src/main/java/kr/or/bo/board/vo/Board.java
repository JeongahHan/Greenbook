package kr.or.bo.board.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardRegDate;
	private int boardReadCount;
	
	private String boardType;
	
	private List fileList; //리스트 타입 객체 선언 
	
	
	//검색필터
	private String type; //검색타입
	private String keyword; //검색내용
	

	
	
	public String getBoardContentBr() {
		//컨텐츠 작성자가 엔터를 입력하면 > 그 엔터를 br로 바꿔주기
		return boardContent.replaceAll("\r\n","<br>");
	}
}
