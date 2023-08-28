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
	/*
	public List selectAllMember() {
		String query = "select * from MEMBER order by 1";
		List list = jdbc.query(query, memberRowMapper);
		return list;
	}*/
	public List selectAdminList(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from member order by 1 desc)n)where rnum between ? and ?";
		List list = jdbc.query(query, memberRowMapper, start, end);
		return list;
	}
	public int selectAdminTotalCount() {
		String query = "select count(*) from member";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	public int changeLevel(int memberNo, int memberLevel) {
		String query = "update MEMBER set MEMBER_LEVEL=? where MEMBER_NO=?";
		Object[] params = {memberLevel, memberNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public Member selectOneMember(String checkId) {
		String query = "select * from member where member_id=?";
		List list = jdbc.query(query, memberRowMapper, checkId);
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
		Object[] params = {memberPw, memberNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public Member checkId(String memberId) {
		String query = "select * from member where member_id = ?";
		List list = jdbc.query(query, memberRowMapper, memberId);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public int insertMember(Member member) {
		String query = "insert into member values(member_seq.nextval,?,?,?,?,?,2,to_char(sysdate,'yyyy-mm-dd'),50)";
		Object[] params = {member.getMemberId(),member.getMemberPw(),member.getMemberName(),member.getMemberPhone(),member.getMemberEmail()};
		int result = jdbc.update(query, params);
		return result;
	}

	public Member checkEmail(String memberEmail) {
		String query = "select * from member where member_email = ?";
		List list = jdbc.query(query, memberRowMapper, memberEmail);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}
	
	public List selectLevelList(int start, int end , String memberlevel) {
		String query = null;
		if(memberlevel.equals("1")) {
		     query = "select * from (select rownum as rnum, n.* from (select * from member where member_level = 1 order by 1 desc)n)where rnum between ? and ?";
		}else if(memberlevel.equals("2")) {
			 query = "select * from (select rownum as rnum, n.* from (select * from member where member_level = 2 order by 1 desc)n)where rnum between ? and ?";
		}else if(memberlevel.equals("3")) {
			 query =  "select * from (select rownum as rnum, n.* from (select * from member where member_level = 3 order by 1 desc)n)where rnum between ? and ?";
		}	
		List list = jdbc.query(query, memberRowMapper, start, end);
		return list;
	}
	
}
