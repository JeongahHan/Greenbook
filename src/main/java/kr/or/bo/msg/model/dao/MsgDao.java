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
		String query = "select * from message where receiver = ? order by 1 desc";
		List list = jdbc.query(query, msgRowMapper, memberId);
		return list;
	}

	public int sendMsgToAdmin(Msg msg) {
		String query = "insert into message values(message_seq.nextval, ?, ?, ?, 0, TO_CHAR(SYSDATE, 'YYYY.MM.DD HH24:MI'))";
		Object[] params = {msg.getSender(), msg.getReceiver(), msg.getMessage()};
		int result = jdbc.update(query, params);
		return result;
	}

	public Msg selectReceiveView(int mid) {
		String query = "select * from message where mid = ?";
		List list = jdbc.query(query, msgRowMapper, mid);
		if(list == null) {
			return null;			
		}else {
			return (Msg)list.get(0);
		}
	}

	public int readMsg(int mid) {
		String query = "update message set read_chk = 1 where mid = ?";
		Object[] params = {mid};
		int result = jdbc.update(query, params);
		return result;
	}
}
