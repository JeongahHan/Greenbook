package kr.or.bo.board.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment> {

	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		BoardComment bc = new BoardComment();
		bc.setBoardCommentContent(rs.getString("board_comment_content"));
		bc.setBoardCommentdate(rs.getString("board_comment_date"));
		bc.setBoardCommentNo(rs.getInt("board_comment_no"));
		bc.setBoardCommentRef(rs.getInt("board_comment_ref"));
		bc.setBoardCommentWriter(rs.getString("board_comment_writer"));
		bc.setBoardRef(rs.getInt("board_ref"));
		
		
		return bc;
	}
	
	
}
