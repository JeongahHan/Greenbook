package kr.or.bo.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductTradeList {

	private int tradeNo;
	private String productBoardTitle;
	private String productAuthor;
	private int productPrice;
	private String productBoardWriter;
	private String tradeRequestDate;
	private String tradeCompleteDone;
	
	private int grade;
	private int productSellCheck;
	
	//거래일 추가
	private String tradeCompleteDate;
	
}
