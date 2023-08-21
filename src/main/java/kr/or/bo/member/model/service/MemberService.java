package kr.or.bo.member.model.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List selectAllMember() {
		List list = memberDao.selectAllMember();
		return list;
	}
	
	@Transactional
	public int changeLevel(int memberNo, int memberLevel) {
		return memberDao.changeLevel(memberNo, memberLevel);
	}

	@Transactional
	public boolean checkedChangeLevel(String no, String level) {
		StringTokenizer sT1 = new StringTokenizer(no, "/");
		StringTokenizer sT2 = new StringTokenizer(level, "/");
		boolean result = true;
		while(sT1.hasMoreTokens()) {
			int memberNo = Integer.parseInt(sT1.nextToken());
			int memberLevel = Integer.parseInt(sT2.nextToken());
			int changeResult = memberDao.changeLevel(memberNo, memberLevel);	
			if(changeResult == 0) {	
				result = false;
				break;	
			}
		}
		return result;
	}
	public Member selectOneMember(String checkId) {
		Member m = memberDao.selectOneMember(checkId);
		return m;
	}


	public Member selectMemberId(String memberName, String memberEmail) {
		Member m = memberDao.selectMemberId(memberName, memberEmail);
		return m;
	}

	public Member selectMemberPw(String memberId, String memberEmail) {
		Member m = memberDao.selectMemberPw(memberId, memberEmail);
		return m;
	}
	
	@Transactional
	public int updatePw(int memberNo, String memberPw) {
		int result = memberDao.updatePw(memberNo, memberPw);
		return result;
	}

	public Member checkId(String memberId) {
		Member m = memberDao.checkId(memberId);
		return m;
	}
	
	@Transactional
	public int insertMember(Member member) {
		int result = memberDao.insertMember(member);
		return result;
	}

	public Member checkEmail(String memberEmail) {
		Member m = memberDao.checkEmail(memberEmail);
		return m;
	}

}
