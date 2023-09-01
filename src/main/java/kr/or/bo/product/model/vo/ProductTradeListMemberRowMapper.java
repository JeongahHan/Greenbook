package kr.or.bo.product.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductTradeListMemberRowMapper implements RowMapper<ProductTradeList>{

	@Override
	public ProductTradeList mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProductTradeList ptl = new ProductTradeList();
		ptl.setProductAuthor(rs.getString("product_author"));
		ptl.setProductBoardTitle(rs.getString("product_board_title"));
		ptl.setProductBoardWriter(rs.getString("product_board_writer"));
		ptl.setProductPrice(rs.getInt("product_price"));
		ptl.setTradeCompleteDone(rs.getString("trade_complete_done"));
		ptl.setTradeNo(rs.getInt("trade_no"));
		ptl.setTradeRequestDate(rs.getString("trade_request_date"));
		ptl.setGrade(rs.getInt("grade"));
		ptl.setProductSellCheck(rs.getInt("product_sell_check"));
		
		ptl.setTradeCompleteDate(rs.getString("trade_complete_date"));
		ptl.setProductBoardNo(rs.getInt("product_Board_No"));
		return ptl;
		
	}

	
	
}
