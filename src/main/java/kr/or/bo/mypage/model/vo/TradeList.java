package kr.or.bo.mypage.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TradeList {
	private int tradeNo;
	private int productBoardNo;
	private String consumer;
	private String tradeRequestDate;
	private String tradeCompleteDate;
}
