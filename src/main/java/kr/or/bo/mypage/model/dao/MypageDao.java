package kr.or.bo.mypage.model.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.board.vo.BoardFileRowMapper;
import kr.or.bo.board.vo.BoardRowMapper;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.member.model.vo.MemberRowMapper;
import kr.or.bo.mypage.model.vo.BoardCommentRowMapperForMypage;
import kr.or.bo.mypage.model.vo.TradeList;
import kr.or.bo.mypage.model.vo.TradeListRowMapper;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductCommentRowMapper;
import kr.or.bo.product.model.vo.ProductFileRowMapper;
import kr.or.bo.product.model.vo.ProductRowMapper;

@Repository
public class MypageDao {
	@Autowired
	private JdbcTemplate jdbc;
	//찜목록 RowMapper추가 필요
	@Autowired
	private ProductRowMapper productRowMapper;
	@Autowired
	private BoardRowMapper boardRowMapper;
	@Autowired
	private ProductFileRowMapper productFileRowmapper;
	@Autowired
	private ProductCommentRowMapper productCommentRowMapper;
	@Autowired
	private BoardCommentRowMapperForMypage boardCommentRowMapperForMypage;
	@Autowired
	private TradeListRowMapper tradeListRowMapper;
	@Autowired
	private MemberRowMapper memberRowMapper;
	@Autowired
	private BoardFileRowMapper boardFileRowMapper;
	
	//회원정보 수정
	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		
		//변수 총 4개
		//비번, 휴대폰, 이메일 3개 수정 아이디받은곳에서
		String query= "UPDATE MEMBER SET MEMBER_PW=? , MEMBER_PHONE=? , MEMBER_EMAIL=? WHERE MEMBER_ID = ?";
		Object[] params = {member.getMemberPw(), member.getMemberPhone(), member.getMemberEmail(), member.getMemberId()};
		int result = jdbc.update(query,params);
		
		return result;
	}

	//내가 판매중인 도서 조회
	public List selectMySellBook(String memberId, int start, int end) {
		// TODO Auto-generated method stub
		
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from PRODUCT_BOARD where PRODUCT_BOARD_WRITER = ? order by 1 DESC)N) where rnum between ? and ?";
		//String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from PRODUCT_FILE full join PRODUCT_BOARD ON (PRODUCT_NO=PRODUCT_BOARD_NO) where PRODUCT_BOARD_writer=? order by 1 desc)N) where rnum between ? and ?";
		
		List list = jdbc.query(query, productRowMapper, memberId, start, end);
		
		
		return list;
	}
	
	//내가 판매중인 도서 이미지 파일경로 구해오기
	public List selectMySellBookImgList(String memberId, int start, int end) {
		// TODO Auto-generated method stub
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from PRODUCT_FILE full join PRODUCT_BOARD ON (PRODUCT_NO=PRODUCT_BOARD_NO) where PRODUCT_BOARD_writer=? order by 1 desc)N) where rnum between ? and ?";
		List list = jdbc.query(query, productFileRowmapper, memberId, start, end);
		
		return list;
	}

	//내가 판매중인 도서 토탈 카운트 세오기
	public int selectMySellBookTotalCount(String memberId) {
		// TODO Auto-generated method stub
		String query = "select count(*) as cnt from PRODUCT_BOARD where PRODUCT_BOARD_WRITER = ?";
		//jdbc.query(query, rowMapper, 위치홀더값 ,,,)
		//query메소드를 사용하는 경우 매번 rowMapper를 제작 (결과가 1개여도)
		//단일 값(행1, 열1)을 조회하는 경우
		int totalCount = jdbc.queryForObject(query, Integer.class, memberId);
				
		return totalCount;
	}

	//내가 작성한 자유게시판 글 조회
	public List selectMyBoardList(String memberId, int start, int end) {
		// TODO Auto-generated method stub
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from BOARD where BOARD_WRITER = ? order by 1 DESC)N) where rnum between ? and ?";
		List list = jdbc.query(query, boardRowMapper, memberId, start, end);


		return list;
	}

	//내가 작성한 자유게시판 게시글 토탈 카운트 세오기
	public int selectMyBoardListTotalCount(String memberId) {
		// TODO Auto-generated method stub
		String query = "select count(*) as cnt from BOARD where BOARD_WRITER = ?";
		int totalCount = jdbc.queryForObject(query, Integer.class, memberId);

		
		return totalCount;
	}

	//회원탈퇴
	public int deleteMember(int memberNo) {
		// TODO Auto-generated method stub
		String query ="delete from member where member_no=?";
		Object[] params = {memberNo};
		int result = jdbc.update(query,params);
		
		return result;
	}

	//중고책방 댓글
	public List selectMyProductBoardComment(String memberId, int start, int end) {
		// TODO Auto-generated method stub
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from PRODUCT_COMMENT where PRODUCT_COMMENT_WRITER=? order by 1 desc)N) where rnum between ? and ?";
		List list = jdbc.query(query, productCommentRowMapper, memberId, start, end);
		
		return list;
	}

	//중고책방 댓글 단 게시글의 제목받아오기
	public List selectMyProductBoardList(int productRef) {
		// TODO Auto-generated method stub
		String query ="select * from PRODUCT_BOARD where product_board_no=?";
		List list = jdbc.query(query, productRowMapper, productRef);
		
		return list;
	}

	//중고책방 댓글 단 게시글으 썸네일 받아오기
	public List selectProductFile(int productRef) {
		// TODO Auto-generated method stub
		String query ="select * from PRODUCT_FILE where PRODUCT_NO=?";
		List list =jdbc.query(query, productFileRowmapper, productRef);
		
		return list;
	}

	//내가 작성한 자유게시판 댓글 조회
	public List selectMyComment(String memberId, int start, int end) {
		// TODO Auto-generated method stub
		String query = "select * from(select ROWNUM AS RNUM, N.* from (select * from BOARD_COMMENT where board_comment_writer=? order by 1 desc)N) where rnum between ? and ?";
		List list = jdbc.query(query, boardCommentRowMapperForMypage, memberId, start, end);
		
		return list;
	}

	//자유게시판 댓글 단 게시글의 제목받아오기
	public List selectMyBoardList(int boardRef) {
		// TODO Auto-generated method stub
		String query ="select * from board where board_no=?";
		List list = jdbc.query(query, boardRowMapper, boardRef);
		
		
		return list;
	}

	//거래목록 인서트
	public int tradeInsert(Member m, Product p) {
		// TODO Auto-generated method stub
		String query ="INSERT INTO TRADE_LIST VALUES(TRADE_LIST_SEQ.NEXTVAL,?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD'),null,default)";
		Object params []= {p.getProductBoardNo(), m.getMemberId()};
		int result = jdbc.update(query, params);
		
		return result;
	}

	//고객 정보 조회
	public List selectConsumer(Product p, Member m, int start, int end) {
		// TODO Auto-generated method stub
		//String query = "SELECT * FROM TRADE_LIST WHERE PRODUCT_BOARD_NO=?";
		//String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from TRADE_LIST where PRODUCT_BOARD_NO=? order by 1 DESC)N) where rnum between ? and ?";
		//먼저 요청한사람이 먼저 뜨도록 desc로 순서 안뒤집음
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from TRADE_LIST full join PRODUCT_BOARD using (PRODUCT_BOARD_NO) where PRODUCT_BOARD_NO=? and PRODUCT_SELL_CHECK=0 order by 1 )N) where rnum between ? and ?";
		Object params []= {p.getProductBoardNo(), start, end};
		List list = jdbc.query(query, tradeListRowMapper,params);
		
		return list;
	}

	//멤버 검색해오기
	public List selectOneMember(String memberId) {
		// TODO Auto-generated method stub
		String query ="select * from member where member_id=?";
		
		List list = jdbc.query(query, memberRowMapper, memberId);
		
		
		return list;
	}

	public List selectByRequestList(String memberId, int start, int end) {
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from TRADE_LIST where CONSUMER = ? order by 1 DESC)N) where rnum between ? and ?";
		List byRequestList = jdbc.query(query, tradeListRowMapper, memberId, start, end);
		return byRequestList;
	}

	public int selectByRequestListTotalCount(String memberId) {
		String query = "select count(*) as cnt from TRADE_LIST where CONSUMER = ?";
		int totalCount = jdbc.queryForObject(query, Integer.class, memberId);
		return totalCount;
	}

	public List selectTradeList(Member m) {
		String query = "select * from trade_list where consumer=?";
		List tradeList = jdbc.query(query, tradeListRowMapper, m.getMemberId());
		return tradeList;
	}

	//독서노트 이미지 가져오기
	public List selectBoardFile(int boardRef) {
		// TODO Auto-generated method stub
		String query ="select * from board_file where board_no = ?";
		List list = jdbc.query(query, boardFileRowMapper, boardRef);
		return list;
	}

	//판매완료
	////product_board의 PRODUCT_SELL_CHECK =1로 바꾸기
	public int soldOutFromProductBoard(TradeList tradeList) {
		// TODO Auto-generated method stub
		String query ="update product_board set PRODUCT_SELL_CHECK = 1 where product_Board_No= ? ";
		int result = jdbc.update(query,tradeList.getProductBoardNo());
		
		return result;
	}

	//trade_list의  TRADE_COMPLETE_DONE =1로 바꾸기
	//TRADE_COMPLETE_DATE 넣어주기
	public int soldOutFromTradeList(TradeList tradeList) {
		// TODO Auto-generated method stub
		String query = "update trade_list set TRADE_COMPLETE_DONE = 1 , TRADE_COMPLETE_DATE=TO_CHAR(SYSDATE, 'YYYY-MM-DD') where trade_No= ? ";
		int result = jdbc.update(query, tradeList.getTradeNo());
		
		return result;
	}
	
}
