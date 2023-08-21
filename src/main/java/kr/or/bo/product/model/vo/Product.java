package kr.or.bo.product.model.vo;

import java.util.List;

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
	private String tradeDate;
	private String filepath;
	
}
