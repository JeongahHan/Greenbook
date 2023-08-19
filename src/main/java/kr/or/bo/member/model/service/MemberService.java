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
}
