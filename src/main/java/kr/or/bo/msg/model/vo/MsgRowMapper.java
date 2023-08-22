package kr.or.bo.msg.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MsgRowMapper implements RowMapper<Msg>{

	@Override
	public Msg mapRow(ResultSet rs, int rowNum) throws SQLException {
		Msg msg = new Msg();
		msg.setMessage(rs.getString("message"));
		msg.setMid(rs.getInt("mid"));
		msg.setReadChk(rs.getInt("read_chk"));
		msg.setReceiver(rs.getString("receiver"));
		msg.setSendDate(rs.getString("send_date"));
		msg.setSender(rs.getString("sender"));
		return msg;
	}
	
}
