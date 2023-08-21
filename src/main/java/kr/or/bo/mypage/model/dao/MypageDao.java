package kr.or.bo.mypage.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.member.model.vo.Member;

@Repository
public class MypageDao {
	@Autowired
	private JdbcTemplate jdbc;
	//찜목록 RowMapper추가 필요

	//회원정보 수정
	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		int result = 0;
		String query= "UPDATE MEMBER SET MEMBER_PW=? , MEMBER_PHONE=? , MEMBER_EMAIL=? WHERE MEMBER_ID = ?";
		
		
		return result;
	}
	
}
