package kr.or.bo.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired 
	private BoardRowMapper boardRowMapper;

//////////////////////////////////////////////////////////////////////////
//게시물 페이지 전체보기
	
	/*
	public List selectBoardList() {
		String query="select * from Board";
		List boardList = jdbc.query(query,boardRowMapper);
		return boardList;
	}
	*/
	
	//1.한 페이지당 게시물 수 지정  ->> 10개
	public List selectBoardList(int start, int end) {
		String query = "select * from(select rownum as rnum, n.* from(select * from board order by 1 desc)n)where rnum between ? and ?";
		List boardList = jdbc.query(query, boardRowMapper,start,end); //boardRowMapper >> rowMapper 조회
		return boardList;
	}
	
	//2.페이지 네비게이션 제작 ->> 끝 번호 뽑아오기
	public int selectBoardTotalCount() {
		String query = "select count(*) from board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	
	
////////////////////////////////////////////////////////////	
//제목:내용 찾기
	//1.한 페이지당 게시물 수 지정  ->> 10개
	public List getSearchList(int start, int end, String type, String keyword) {
			
		String query = null;
		
		if(type.equals("title")) {
			query = "select * from (select rownum as snum, s.* from ((select * from (select* from (select rownum as rnum, n.* from(select * from board order by 1 desc)n) where board_title like UPPER('%'||?||'%')))s)) where snum between ? and ?";
		}else if(type.equals("content")) {
			query = "select * from (select rownum as snum, s.* from ((select * from (select* from (select rownum as rnum, n.* from(select * from board order by 1 desc)n) where board_content like UPPER('%'||?||'%')))s)) where snum between ? and ?";
		}else if(type.equals("writer")){
			query = "select * from (select rownum as snum, s.* from ((select * from (select* from (select rownum as rnum, n.* from(select * from board order by 1 desc)n) where board_writer like UPPER('%'||?||'%')))s)) where snum between ? and ?";
		}
		List boardList = jdbc.query(query, boardRowMapper,keyword,start,end);
		return boardList;
	}

	//2.페이지 네비게이션 제작 ->> 끝 번호 뽑아오기
		public int getSearchListTotalCount(String type, String keyword) {
			
			String query = null;
			
			if(type.equals("title")) {
				query = "select count(*) from (select rownum as snum, s.* from ((select * from (select * from (select rownum as rnum, n.* from (select * from board order by 1 desc)n) where board_title like UPPER('%'||?||'%')))s))";
			}else if(type.equals("content")) {
				query = "select count(*) from (select rownum as snum, s.* from ((select * from (select * from (select rownum as rnum, n.* from (select * from board order by 1 desc)n) where board_content like UPPER('%'||?||'%')))s))";
			}else if(type.equals("writer")){
				query = "select count(*) from (select rownum as snum, s.* from ((select * from (select * from (select rownum as rnum, n.* from (select * from board order by 1 desc)n) where board_writer like UPPER('%'||?||'%')))s))";
			}
			
			int totalCount = jdbc.queryForObject(query,Integer.class,keyword);
			return totalCount;
	}

	
		
//////////////////////////////////////////////////////////////////////////////////
//자유게시판 글 상세보기
		
		
		public int updateReadCount(int boardNo) {
			String query ="update board set read_count = read_count+1 where board_no=?";
			Object [] params = {boardNo};
			int result = jdbc.update(query,params);
			return result;
		}

		public Board selectOneBoard(int boardNo) {
			// TODO Auto-generated method stub
			return null;
		}

		public List selectBoardFile(int boardNo) {
			// TODO Auto-generated method stub
			return null;
		}

		public List selectCommentList(int boardNo, int memberNo) {
			// TODO Auto-generated method stub
			return null;
		}

		public List selectReCommentList(int boardNo, int memberNo) {
			// TODO Auto-generated method stub
			return null;
		}		
		
		
		
		
		
		
}//다오 종료
