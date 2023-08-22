package kr.or.bo.msg.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.msg.model.vo.MsgRowMapper;

@Repository
public class MsgDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MsgRowMapper msgRowMapper;
}
