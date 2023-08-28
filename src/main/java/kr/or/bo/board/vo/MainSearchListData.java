package kr.or.bo.board.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainSearchListData {

		private List productList;
		private List boardList;
		private String pageNavi;
	


	
}
