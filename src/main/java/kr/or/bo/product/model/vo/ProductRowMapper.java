package kr.or.bo.product.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product p = new Product();
		p.setProductAuthor(rs.getString("product_author"));
		p.setProductBoardContent(rs.getString("product_board_content"));
		p.setProductBoardNo(rs.getInt("product_board_no"));
		p.setProductBoardTitle(rs.getString("product_board_title"));
		p.setProductBoardWriter(rs.getString("product_board_writer"));
		p.setProductCondition(rs.getInt("product_condition"));
		p.setProductPrice(rs.getInt("product_price"));
		p.setProductRegDate(rs.getString("product_reg_date"));
		p.setProductSellCheck(rs.getInt("product_sell_check"));
		p.setReadCount(rs.getInt("read_count"));
		return p;
	}

}
