package kr.or.bo.wish.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishList {
	private int wishListNo;
	private int productBoardNo;
	private String memberId;
	private String productBoarWriter;
	private int productPrice;
	private int productSellCheck;
	private String productBoardTitle;
	private String filepath;
}
