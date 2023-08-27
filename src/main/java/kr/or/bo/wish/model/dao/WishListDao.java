package kr.or.bo.wish.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.wish.model.vo.WishListRowMapper;

@Repository
public class WishListDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private WishListRowMapper wishListRowMapper;
	
	public int insertWish(int productBoardNo, String memberId) {
		String query = "insert into wish_list values(wish_list_seq.nextval, ?, ?)";
		Object[] params = {productBoardNo, memberId};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteWish(int productBoardNo, String memberId) {
		String query = "delete from wish_list where product_board_no = ? and member_id = ?";
		Object[] params = {productBoardNo, memberId}; 
		int result = jdbc.update(query, params);
		return result;
	}
}
