package kr.or.bo.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.member.model.vo.MemberRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	
	public Member selectOneMember(String memberId, String memberPw) {
		String query = "select * from member where member_id = ? and member_pw = ?";
		List list = jdbc.query(query, memberRowMapper, memberId, memberPw);
		if(list.isEmpty()) {
			return null;			
		}
		return (Member)list.get(0);
	}

	public Member selectMemberId(String memberName, String memberEmail) {
		String query = "select * from member where member_name =? and member_email = ?";
		List list = jdbc.query(query, memberRowMapper, memberName, memberEmail);
		if(list.isEmpty()) {
			return null;			
		}
		return (Member)list.get(0);
	}

	public Member selectMemberPw(String memberId, String memberEmail) {
		String query = "select * from member where member_id = ? and member_email = ?";
		List list = jdbc.query(query, memberRowMapper, memberId, memberEmail);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public int updatePw(int memberNo, String memberPw) {
		String query = "update member set member_pw = ? where member_no = ?";
		Object[] params = {memberNo, memberPw};
		int result = jdbc.update(query, params);
		return result;
	}
}
