package kr.or.bo.mypage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bo.member.model.service.MemberService;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.service.MypageService;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {
	@Autowired
	private MypageService mypageService;
	//내가만든거 아님
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value = "/memberUpdateFrm")
	public String memberUpdateFrm () {
		
		return "mypage/myBoard";
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
	public String update(Member member, HttpSession session) {
		// disabled 때문인거 같은데 멤버 비밀번호, 휴대폰, 이메일만 넘겨받음
		int result = mypageService.updateMember(member);
		//로그인되있는 세션 정보 바꿔주기
		//메소드 가져다 쓰기
		Member m = memberService.selectOneMember(member.getMemberId(), member.getMemberPw());
		session.setAttribute("m", m);
		System.out.println(m);
		
		return "mypage/memberUpdateFrm";//에러땜에 임시로 여기로감
	}
	
	@GetMapping(value = "/myWishList")
	public String myWishList() {
		return "mypage/myWishList";
	}
	
}
