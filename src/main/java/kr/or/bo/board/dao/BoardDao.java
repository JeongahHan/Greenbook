package kr.or.bo.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardComment;
import kr.or.bo.board.vo.BoardCommentRowMapper;
import kr.or.bo.board.vo.BoardFile;
import kr.or.bo.board.vo.BoardFileRowMapper;
import kr.or.bo.board.vo.BoardRowMapper;
import kr.or.bo.board.vo.MainSearchListRowMapper;
import kr.or.bo.product.model.vo.ProductFile;
import kr.or.bo.product.model.vo.ProductFileRowMapper;
import kr.or.bo.product.model.vo.ProductRowMapper;

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
	
	@Autowired
	private ProductRowMapper productRowMapper;
	@Autowired
	private ProductFileRowMapper productFileRowmapper;
	
	@Autowired
	private MainSearchListRowMapper mainSearchListRowMapper;

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
			query = "select * from (select rownum as snum, s.* from ((select * from (select* from (select rownum as rnum, n.* from(select * from board order by 1 desc)n) where board_writer like '%'||?||'%'))s)) where snum between ? and ?";
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
				query = "select count(*) from (select rownum as snum, s.* from ((select * from (select * from (select rownum as rnum, n.* from (select * from board order by 1 desc)n) where board_writer like '%'||?||'%'))s))";
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


/////////////////////게시글 삭제
		public int deleteBoard(int boardNo) {
			String query = "delete from board where board_no=?";
			Object[] params = {boardNo};
			int result = jdbc.update(query,params);
			return result;
		}
		
//////////////////////게시글 수정

		public int updateBoard(Board b) {
			String query = "update board set board_title=?,board_content=? where board_no =? ";
			Object[] params = {b.getBoardTitle(), b.getBoardContent(), b.getBoardNo()};
			int result = jdbc.update(query,params);
			return result;
		}
		
		//파일조회
		public BoardFile selectOneFile(int fileNo) {
			String query = "select * from board_file where file_no=?";
			List list = jdbc.query(query, boardFileRowMapper, fileNo);
			return (BoardFile)list.get(0);
		}
		//파일 삭제
		public int deleteFile(int fileNo) {
			String query = "delete from board_file where file_no=?";
			Object[] params = {fileNo};
			int result = jdbc.update(query,params);
			return result;
		}

		
/////////////////////
//댓글 등록
		public int insertComment(BoardComment bc) {
			String query = "insert into board_comment values(board_comment_seq.nextval,?,?,to_char(sysdate,'yyyy-mm-dd'),?,?)";
			String boardCommentRef = bc.getBoardCommentRef()==0? null:String.valueOf(bc.getBoardCommentRef());
			Object[] params = {bc.getBoardCommentWriter(),bc.getBoardCommentContent(),bc.getBoardRef(),boardCommentRef};
			int result = jdbc.update(query,params);
			return result;
		}
/////////////////////////////////////////////////		
//댓글 대댓글 수정		
		public int updateComment(BoardComment bc) {
			String query = "update board_comment set board_comment_content=? where board_comment_no=?";
			Object[] params = {bc.getBoardCommentContent(),bc.getBoardCommentNo()};
			int result = jdbc.update(query,params);
			return result;
		}
/////////////////////////////////////////////////		
//댓글 대댓글 삭제		
		public int deleteComment(int boardCommentNo) {
			String query = "delete from board_comment where board_comment_no=?";
			Object[] params = {boardCommentNo};
			int result = jdbc.update(query,params);
			return result;
		}

//////////////////////////////////////////////////
//댓글 좋아요/좋아요취소	
		
		
		public int insertCommentLike(int boardCommentNo, int memberNo) {
			String query = "insert into board_comment_like values(?,?)";
			Object[] params = {boardCommentNo,memberNo};
			int result = jdbc.update(query,params);
			return result;
		}

		public int removeCommentLike(int boardCommentNo, int memberNo) {
			String query = "delete from board_comment_like where board_comment_no=? and member_no=?";
			Object[] params = {boardCommentNo,memberNo};
			int result = jdbc.update(query,params);
			return result;
		}

		public int likeCount(int boardCommentNo) {
			String query = "select count(*) from board_comment_like where board_comment_no=?";
			int likeCount = jdbc.queryForObject(query, Integer.class,boardCommentNo);
			return likeCount ; 
		}

		
		
/////////////////////////////////////////////////////////////
//메인서치기능		


		public List mainSearchList1(int start, int end, String keyword) {
			String query = "select * from (select rownum as rnum,r.* from (select * from (select  b.board_no, b.BOARD_TITLE, b.board_content, b.board_writer,b.board_read_count,b.board_reg_date,'board' as board_type from board b union all select p.product_board_no, p.product_board_title, (p.product_author || p.product_board_content),p.product_board_writer,p.read_count,p.product_reg_date,'product_board' from product_board p) where (board_title||board_content) like '%'||?||'%' order by board_reg_date desc)r) where rnum between ? and ?";
			List mainSearchList = jdbc.query(query, mainSearchListRowMapper, keyword, start, end);
			return mainSearchList;
		}


		public int getSearchListTotalCount1(String keyword) {
			String query = "select count(*) from (select rownum as rnum,r.* from (select * from (select  b.board_no, b.BOARD_TITLE, b.board_content, b.board_writer,b.board_read_count,b.board_reg_date,'board' as board_type from board b union all select p.product_board_no, p.product_board_title, (p.product_author || p.product_board_content),p.product_board_writer,p.read_count,p.product_reg_date,'product_board' from product_board p) where (board_title||board_content) like '%'||?||'%' order by board_reg_date desc)r)";
			int totalCount = jdbc.queryForObject(query, Integer.class, keyword);
			return totalCount;
		}		
		
		
}//다오 종료
