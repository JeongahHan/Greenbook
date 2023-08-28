package kr.or.bo.wish.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class WishListRowMapper implements RowMapper<WishList> {

	@Override
	public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
		WishList wish = new WishList();
		wish.setMemberId(rs.getString("member_id"));
		wish.setProductBoardNo(rs.getInt("product_board_no"));
		wish.setWishListNo(rs.getInt("wish_list_no"));
		wish.setFilepath(rs.getString("filepath"));
		wish.setProductBoardTitle(rs.getString("product_board_title"));
		wish.setProductBoarWriter(rs.getString("product_board_writer"));
		wish.setProductPrice(rs.getInt("product_price"));
		wish.setProductSellCheck(rs.getInt("product_sell_check"));
		return wish;
	}

}
