package kr.or.bo.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bo.member.model.dao.MemberDao;
import kr.or.bo.member.model.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public Member selectOneMember(String memberId, String memberPw) {
		Member m = memberDao.selectOneMember(memberId, memberPw);
		return m;
	}

	public Member selectMemberId(String memberName, String memberEmail) {
		Member m = memberDao.selectMemberId(memberName, memberEmail);
		return m;
	}
}
