package kr.or.bo.board.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class boardFileRowMapper implements RowMapper<BoardFile>{

	@Override
	public BoardFile mapRow(ResultSet rs, int rowNum) throws SQLException{
		BoardFile file = new BoardFile();
		
		file.setFileNo(rs.getInt("file_no"));
		file.setBoardNo(rs.getInt("board_no"));
		file.setFilename(rs.getString("filename"));
		file.setFilepath(rs.getString("filepath"));
		
		return file;
		
	}
	
	
	
}
