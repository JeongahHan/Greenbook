package kr.or.bo.mypage.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MypageDao {
	@Autowired
	private JdbcTemplate jdbc;
	//찜목록 RowMapper추가 필요
	
}
