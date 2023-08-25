package kr.or.bo.mypage.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.board.vo.BoardRowMapper;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.vo.BoardCommentRowMapperForMypage;
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
		System.out.println("여기까지 잘오나 dao");
		List list = jdbc.query(query, boardCommentRowMapperForMypage, memberId, start, end);
		
		return list;
	}


	
}
