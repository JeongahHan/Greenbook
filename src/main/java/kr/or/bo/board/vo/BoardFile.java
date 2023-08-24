package kr.or.bo.board.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardFile {
	private int fileNo;
	private int boardNo;
	private String filename;
	private String filepath;
}
