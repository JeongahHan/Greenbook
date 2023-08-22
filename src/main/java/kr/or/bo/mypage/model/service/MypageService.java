package kr.or.bo.mypage.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.dao.MypageDao;
import kr.or.bo.product.model.vo.Product;

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

	//내가 판매중인 도서 조회해오기
	public List<Product> selectMySellBook(String memberId, int reqPage) {
		// TODO Auto-generated method stub
		
		List list = mypageDao.selectMySellBook(memberId,reqPage);
		
		//여기서 페이지 나비 만들기
		
		return list;
	}//selectMySellBook() 종료
	
}
