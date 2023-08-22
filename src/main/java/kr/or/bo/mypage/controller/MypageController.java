package kr.or.bo.mypage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bo.member.model.service.MemberService;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.service.MypageService;
import kr.or.bo.mypage.model.vo.MypageListData;
import kr.or.bo.product.model.vo.Product;

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
		//임시 로그인확인 코드
		
		return "mypage/memberUpdateFrm";
	}
	
	@GetMapping(value = "/myBoard")
	public String myBoard(int reqPage) {
		
		//내가 쓴 게시물만 조회 해오기 //아직 Board Vo가 없음
		//ArrayList<Board> list = mypageService.selectMyBoardList();
		System.out.println(reqPage);
		//내가 작성한 자유게시판 select 해오기
		//MypageListData mld2 = mypageService.selectMyBoardList();

		
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
	
	@GetMapping(value = "/mySellBook")
	public String mySellBook(HttpSession session,Model model, int reqPage) {
		Member m = (Member)session.getAttribute("m");
		System.out.println(reqPage);
		System.out.println(m.getMemberId());
		
		//내가 판매중인 도서 select 해오기
		MypageListData mld =mypageService.selectMySellBook(m.getMemberId(), reqPage);
		
		model.addAttribute("mySellBookList", mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		
		return "mypage/mySellBook";
	}
	
}
