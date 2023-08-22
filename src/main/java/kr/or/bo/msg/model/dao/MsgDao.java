package kr.or.bo.msg.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.msg.model.vo.Msg;
import kr.or.bo.msg.model.vo.MsgRowMapper;

@Repository
public class MsgDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MsgRowMapper msgRowMapper;
	
	public List selectReceiveList(String memberId) {
		String query = "select * from message where receiver = ?";
		List list = jdbc.query(query, msgRowMapper, memberId);
		return list;
	}
}
