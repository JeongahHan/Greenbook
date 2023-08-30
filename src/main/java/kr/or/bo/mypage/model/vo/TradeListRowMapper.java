package kr.or.bo.mypage.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TradeListRowMapper implements RowMapper<TradeList>{

	@Override
	public TradeList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		TradeList tl = new TradeList();
		
		tl.setTradeNo(rs.getInt("trade_no"));
		tl.setProductBoardNo(rs.getInt("product_board_no"));
		tl.setConsumer(rs.getString("consumer"));
		tl.setTradeRequestDate(rs.getString("trade_request_date"));
		tl.setTradeCompleteDate(rs.getString("TRADE_COMPLETE_DATE"));
		
		return tl;
	}

	
}
