package kr.or.bo.product.model.vo;

import java.util.List;

import kr.or.bo.mypage.model.vo.TradeList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

	private int productBoardNo;
	private String productBoardTitle;
	private String productBoardContent;
	private String productBoardWriter;
	private String productRegDate;
	private int productPrice;
	private String productAuthor;
	private int productCondition;
	private int productSellCheck;
	private int readCount;
	private ProductFile productFile;
	private List fileList;
	
	//구매 요청 확인 버튼 을위해 추가
	private TradeList tradeList;
	
}
