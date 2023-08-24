package kr.or.bo.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardCommentRowMapper;
import kr.or.bo.board.vo.BoardFile;
import kr.or.bo.board.vo.BoardFileRowMapper;
import kr.or.bo.board.vo.BoardRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired 
	private BoardRowMapper boardRowMapper;
	@Autowired
	private BoardFileRowMapper boardFileRowMapper;
	@Autowired
	private BoardCommentRowMapper boardCommentRowMapper;

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
			String query ="update board set board_read_count = board_read_count+1 where board_no=?";
			Object [] params = {boardNo};
			int result = jdbc.update(query,params);
			return result;
		}

		public Board selectOneBoard(int boardNo) {
			String query = "select * from board where board_no=?";
			List list = jdbc.query(query, boardRowMapper,boardNo);
			return (Board)list.get(0); //Board 형태로 전달
		}

		public List selectBoardFile(int boardNo) {
			String query = "select * from board_file where board_no=?";
			List list = jdbc.query(query,boardFileRowMapper,boardNo);
			return list;
		}		
		

		public List selectCommentList(int boardNo, int memberNo) {
			String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_Like,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count from (select * from board_comment where board_ref=? and board_comment_ref is null order by 1)bc"; 
			List list = jdbc.query(query,boardCommentRowMapper,memberNo,boardNo);
			
			return list;
		}

		public List selectReCommentList(int boardNo, int memberNo) {
			String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_Like,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count from (select * from board_comment where board_ref=? and board_comment_ref is not null order by 1)bc"; 
			List list = jdbc.query(query,boardCommentRowMapper,memberNo,boardNo);
			
			return list;
		}

//////////////////////////게시판 작성
		public int insertBoard(Board b) {
			String query = "insert into board values(board_seq.nextval,?,?,?,to_char(sysdate,'yyyy-mm-dd'),0)";
			Object [] params = {b.getBoardTitle(),b.getBoardContent(),b.getBoardWriter()};
			int result = jdbc.update(query,params);
			return result;
		}

		public int getBoard() {
			String query = "select max(board_no) from board"; //방금 인서트 된거중에 제일 큰 board no 가져옴 (다른사람들꺼와 겹칠일 없음 왜냐면 커밋 기준으로 거르고오는거기 때문)
			int boardNo = jdbc.queryForObject(query, Integer.class);
			return boardNo;
		}

		public int insertBoardFile(BoardFile file) {
			String query = "insert into board_file values (board_file_seq.nextval,?,?,?)";
			Object[] params = {file.getBoardNo(),file.getFilename(),file.getFilepath()};
			int result =  jdbc.update(query,params);
			return result;
		}


		
		
		
		
		
}//다오 종료
