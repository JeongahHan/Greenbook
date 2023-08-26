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
		return wish;
	}

}
