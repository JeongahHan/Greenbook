package kr.or.bo.mypage.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.dao.MypageDao;

@Service
public class MypageService {
	@Autowired
	private MypageDao mypageDao;

	//회원정보 수정
	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		int result = mypageDao.updateMember(member);
		
		return result;
	}//업데이트 멤버 종료
	
}
