package kr.or.bo.mypage.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.or.bo.board.vo.BoardComment;

@Component
public class BoardCommentRowMapperForMypage implements RowMapper<BoardComment> {
//마이페이지에서 쓸 rowmapper 하나 더만듬
	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		BoardComment bc = new BoardComment();
		
		bc.setBoardCommentNo(rs.getInt("board_comment_no"));
		bc.setBoardCommentWriter(rs.getString("board_comment_writer"));
		bc.setBoardCommentContent(rs.getString("board_comment_content"));
		bc.setBoardCommentDate(rs.getString("board_comment_date"));
		bc.setBoardRef(rs.getInt("board_ref"));
		bc.setBoardCommentRef(rs.getInt("board_comment_ref"));
		
		return bc;
	}
	
	
}
