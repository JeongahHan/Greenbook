package kr.or.bo.wish.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class wishListMainRowMapper implements RowMapper<WishList> {

	@Override
	public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
		WishList wish = new WishList();
		wish.setProductBoardNo(rs.getInt("product_board_no"));
		//wish.setWishListNo(rs.getInt("wish_list_no"));
		wish.setProductBoardTitle(rs.getString("PRODUCT_BOARD_TITLE"));
		wish.setFilepath(rs.getString("FILEPATH"));
		return wish;
	}
}
