package kr.or.bo.mypage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.service.MypageService;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {
	@Autowired
	private MypageService mypageService;
	
	@GetMapping(value = "/memberUpdateFrm")
	public String memberUpdateFrm () {
		
		return "mypage/memberUpdateFrm";
	}
	
	@GetMapping(value = "/myBoard")
	public String myBoard() {
		return "mypage/myBoard";
	}
	
	@GetMapping(value = "/myComment")
	public String myComment() {
		return "mypage/myComment";
	}
	@PostMapping(value = "/update")
	public String update(Member member) {
		// disabled 때문인거 같은데 멤버 비밀번호, 휴대폰, 이메일만 넘겨받음
		System.out.println(member);
		int result = mypageService.updateMember(member);
		
		return "mypage/memberUpdateFrm";//에러땜에 임시로 여기로감
	}
	
}
