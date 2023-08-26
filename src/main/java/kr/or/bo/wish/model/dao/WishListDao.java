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
}
