package kr.or.bo.board.vo;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardRowMapper  implements RowMapper<Board>{
	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		
		b.setBoardNo(rs.getInt("Board_NO"));
		b.setBoardTitle(rs.getString("Board_TITLE"));
		b.setBoardContent(rs.getString("Board_CONTENT"));
		b.setBoardWriter(rs.getString("Board_WRITER"));
		b.setBoardRegDate(rs.getString("Board_REG_DATE"));
		b.setBoardReadCount(rs.getInt("Board_READ_COUNT"));
		
		return b;
	}
}