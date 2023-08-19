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
			int changeResult = memberDao.changeLevel(memberNo, memberLevel);	//레벨변경 재활용
			if(changeResult == 0) {	//실패
				result = false;
				break;	//한 번이라도 실패가 일어나면 더 할 필요가 없으니까 내보냄
			}
		}
		return result;	//한 번이라도 실패 : false, 전부 성공하면 true
	}
	public Member selectOneMember(String checkId) {
		Member m = memberDao.selectOneMember(checkId);
		return m;
	}
}
