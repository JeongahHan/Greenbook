package kr.or.bo.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberRowMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member m = new Member();
		m.setEnrollDate(rs.getString("enroll_date"));
		m.setGrade(rs.getInt("grade"));
		m.setMemberEmail(rs.getString("member_email"));
		m.setMemberId(rs.getString("member_id"));
		m.setMemberLevel(rs.getInt("member_level"));
		m.setMemberName(rs.getString("member_name"));
		m.setMemberNo(rs.getInt("member_no"));
		m.setMemberPhone(rs.getInt("member_phone"));
		m.setMemberPw(rs.getString("member_pw"));
		return m;
	}	
}
