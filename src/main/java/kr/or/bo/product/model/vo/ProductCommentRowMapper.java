package kr.or.bo.product.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductCommentRowMapper implements RowMapper<ProductComment>{

	@Override
	public ProductComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductComment pc = new ProductComment();
		pc.setProductCommentContent(rs.getString("product_comment_content"));
		pc.setProductCommentDate(rs.getString("product_comment_date"));
		pc.setProductCommentNo(rs.getInt("product_comment_no"));
		pc.setProductCommentRef(rs.getInt("product_comment_ref"));
		pc.setProductCommentWriter(rs.getString("product_comment_writer"));
		pc.setProductRef(rs.getInt("product_ref"));
		return pc;
	}
	
}
