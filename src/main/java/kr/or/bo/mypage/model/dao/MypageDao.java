package kr.or.bo.mypage.model.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardComment;
import kr.or.bo.board.vo.BoardFileRowMapper;
import kr.or.bo.board.vo.BoardRowMapper;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.member.model.vo.MemberRowMapper;
import kr.or.bo.mypage.model.vo.BoardCommentRowMapperForMypage;
import kr.or.bo.mypage.model.vo.TradeList;
import kr.or.bo.mypage.model.vo.TradeListRowMapper;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductComment;
import kr.or.bo.product.model.vo.ProductCommentRowMapper;
import kr.or.bo.product.model.vo.ProductFileRowMapper;
import kr.or.bo.product.model.vo.ProductRowMapper;
import kr.or.bo.product.model.vo.ProductTradeListMemberRowMapper;
import kr.or.bo.product.model.vo.ProductTradeListRowMapper;

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
	@Autowired
	private ProductTradeListRowMapper productTradeListRowMapper;
	@Autowired
	private ProductTradeListMemberRowMapper productTradeListMemberRowMapper;
	
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
		
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from PRODUCT_BOARD where PRODUCT_BOARD_WRITER = ?  and product_sell_check=0 order by 1 DESC)N) where rnum between ? and ?";
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
		String query = "select count(*) as cnt from PRODUCT_BOARD where PRODUCT_BOARD_WRITER = ? and product_sell_check=0";
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

	//독서노트 댓글 조회
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
		//먼저 요청한사람이 먼저 뜨도록 desc로 순서 안뒤집고 order by 2
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from TRADE_LIST full join PRODUCT_BOARD using (PRODUCT_BOARD_NO) where PRODUCT_BOARD_NO=? and PRODUCT_SELL_CHECK=0 order by 2 )N) where rnum between ? and ?";
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

	//거래일 넘겨주기 추가
	public List selectByRequestList(String memberId, int start, int end) {
		//String query = "SELECT * FROM(SELECT ROWNUM AS RNUM,N.* FROM(SELECT T.TRADE_NO, P.PRODUCT_BOARD_TITLE, P.PRODUCT_AUTHOR, P.PRODUCT_PRICE, P.PRODUCT_BOARD_WRITER, T.TRADE_REQUEST_DATE, T.TRADE_COMPLETE_DONE, M.GRADE, P.PRODUCT_SELL_CHECK FROM TRADE_LIST T, PRODUCT_BOARD P , MEMBER M WHERE T.PRODUCT_BOARD_NO = P.PRODUCT_BOARD_NO AND T.CONSUMER = ? AND P.PRODUCT_BOARD_WRITER = M.MEMBER_ID ORDER BY 1 DESC)N) WHERE RNUM BETWEEN ? AND ?";
		//쿼리문 수정 거래일 추가해서
		String query= "SELECT * FROM (SELECT ROWNUM AS RNUM,N.* FROM (SELECT T.TRADE_NO, P.PRODUCT_BOARD_TITLE, P.PRODUCT_AUTHOR, P.PRODUCT_PRICE,  P.PRODUCT_BOARD_WRITER, T.TRADE_REQUEST_DATE,  T.TRADE_COMPLETE_DONE, T.TRADE_COMPLETE_DATE, M.GRADE, P.PRODUCT_SELL_CHECK, P.PRODUCT_BOARD_NO FROM TRADE_LIST T, PRODUCT_BOARD P , MEMBER M WHERE T.PRODUCT_BOARD_NO = P.PRODUCT_BOARD_NO AND T.CONSUMER = ? AND P.PRODUCT_BOARD_WRITER = M.MEMBER_ID ORDER BY 1 DESC)N) WHERE RNUM BETWEEN ? AND ?";
		// 아이디 비트윈 스타트 앤드
		Object[] params = {memberId, start, end};
		List byRequestList = jdbc.query(query, productTradeListMemberRowMapper, params);
//		List byRequestList = jdbc.query(query, productTradeListRowMapper, memberId);
		System.out.println("여기는 마이페이지 Dao");
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

	//trade_list의  TRADE_COMPLETE_DONE =1로 바꾸기 //필요없는 메소드 되버렷네...
	//TRADE_COMPLETE_DATE 넣어주기
	public int soldOutFromTradeList(TradeList tradeList) {
		// TODO Auto-generated method stub
		String query = "update trade_list set  TRADE_COMPLETE_DATE=TO_CHAR(SYSDATE, 'YYYY-MM-DD') where trade_No= ? ";
		int result = jdbc.update(query, tradeList.getTradeNo());
		
		return result;
	}

//	//tradeList 중복 insert를 막기위해 추가
//	public String selectBuyRequester(int productBoardNo) {
//		// TODO Auto-generated method stub
//		String query = "select * from trade_list where product_board_no = ?";
//		List list = jdbc.query(query, tradeListRowMapper, productBoardNo);
//		System.out.println("여기는 마이페이지 dao : " + productBoardNo);
//
//		TradeList tradeList;
//		if(!list.isEmpty()) {
//			tradeList = (TradeList) list.get(0);
//			System.out.println("여기는 마이페이지 dao : " + tradeList);
//			return tradeList.getConsumer();
//		}
//		return null;
//	}

	//구매요청 중복 막기
	public int selectIsBuyRequest(int productBoardNo, String buyRequester) {
		// TODO Auto-generated method stub
		String query ="select * from trade_list where product_board_no = ? and consumer=?";
		Object params[]= {productBoardNo, buyRequester};
		List list = jdbc.query(query, tradeListRowMapper, params);
		if(list.isEmpty()) {
			return 0;
		}else {
			return 1;
		}
		
	}

	//거래요청 취소
	public int tradeDelete(Member m, Product p) {
		// TODO Auto-generated method stub
		String query = "delete from trade_list where product_board_no = ? and consumer= ? ";
		int result = jdbc.update(query, p.getProductBoardNo(), m.getMemberId());
		
		
		
		return result;
	}

	//독서노트 댓글 토탈 카운트 조회
	public int selectMyBoardCommentTotalCount(String memberId) {
		// TODO Auto-generated method stub
		String query ="select count(*) as cnt from BOARD_COMMENT where BOARD_COMMENT_WRITER = ?";
		int totalCount = jdbc.queryForObject(query, Integer.class,memberId);
		
		return totalCount;
	}

	//중고책방 댓글 토탈 카운트 조회
	public int selectMyProductBoardCommentTotalCount(String memberId) {
		// TODO Auto-generated method stub
		String query ="select count(*) as cnt from PRODUCT_COMMENT where PRODUCT_COMMENT_WRITER = ?";
		int totalCount = jdbc.queryForObject(query, Integer.class,memberId);
		
		return totalCount;
	}

	//고객정보보기 토탈 카운트 조회
	public int selectConsumerTotalCount(int productBoardNo) {
		// TODO Auto-generated method stub
		String query ="select count(*) as cnt from trade_list where product_board_no=?";
		int totalCount = jdbc.queryForObject(query, Integer.class,productBoardNo);
		
		return totalCount;
	}

	//나의 독서 노트 삭제
	public int myBoardDelete(Board board) {
		// TODO Auto-generated method stub
		String query ="delete from board where board_no=?";
		int result = jdbc.update(query, board.getBoardNo());
		
		return result;
	}

	//독서노트 댓글 삭제
	public int myCommentDelete(BoardComment boardComment) {
		// TODO Auto-generated method stub
		String query ="delete from board_comment where board_comment_no=?";
		int result = jdbc.update(query, boardComment.getBoardCommentNo());
		
		return result;
	}

	//중고책방 댓글 삭제
	public int myProductBoardCommentDelete(ProductComment productComment) {
		// TODO Auto-generated method stub
		String query = "delete from PRODUCT_COMMENT where PRODUCT_COMMENT_NO= ?";
		int result =jdbc.update(query, productComment.getProductCommentNo());
		return result;
	}

	public int gradeUp(String writer) {
		String query = "update member set grade = grade+1 where member_id = ?";
		int result = jdbc.update(query, writer);
		return result;
	}

	public int gradeDown(String writer) {
		String query = "update member set grade = grade-1 where member_id = ?";
		int result = jdbc.update(query, writer);
		return result;
	}

	public int tradeCompleteDoneUpdate(String tradeNo) {
		String query = "update trade_list set trade_complete_done = 1 where trade_no = ?";
		int result2 = jdbc.update(query, tradeNo);
		return result2;
	}

	//구매요청버튼을 위해 추가
	public List selectConsumer(Product product) {
		// TODO Auto-generated method stub
		String query ="select * from trade_list where product_board_no=?";
		List list = jdbc.query(query, tradeListRowMapper, product.getProductBoardNo());
		return list;
	}
	
}
