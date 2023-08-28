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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.bo.board.vo.BoardComment;
import kr.or.bo.member.model.service.MemberService;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.service.MypageService;
import kr.or.bo.mypage.model.vo.MypageListData;
import kr.or.bo.product.model.service.ProductService;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductListData;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {
	@Autowired
	private MypageService mypageService;
	//내가만든거 아님
	@Autowired
	private MemberService memberService;
	
	// 명훈
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/memberUpdateFrm")
	public String memberUpdateFrm () {
		//임시 로그인확인 코드
		
		return "mypage/memberUpdateFrm";
	}
	
	//내가작성한 자유게시판 게시물
	@GetMapping(value = "/myBoard")
	public String myBoard(HttpSession session,Model model,int reqPage) {
		
		//내가 쓴 게시물만 조회 해오기 //아직 Board Vo가 없음
		//ArrayList<Board> list = mypageService.selectMyBoardList();
		System.out.println(reqPage);
		Member m = (Member)session.getAttribute("m");
		System.out.println(reqPage);
		System.out.println(m.getMemberId());
		
		//내가 작성한 자유게시판 select 해오기
		MypageListData mld = mypageService.selectMyBoardList(m.getMemberId(),reqPage);
		
		model.addAttribute("myBoardList",mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		
		return "mypage/myBoard";
	}
	@GetMapping(value = "/myBoardDelete")
	public String myBoardDelete() {
		
		return "redirect:/mypage/myBoard?reqPage=1";
	}
	
	//나의 댓글
	@GetMapping(value = "/myComment")
	public String myComment(HttpSession session,Model model, int reqPage) {
		//내가 작성한 댓글 조회해서 넘겨주기
		Member m = (Member) session.getAttribute("m");
		MypageListData mld = mypageService.selectMyComment(m, reqPage);
		
		//서비스에서 제목받아오기
		model.addAttribute("myBoardCommentList",mld.getMypageList()); //리스트넘김
		model.addAttribute("pageNavi", mld.getPageNavi());

		return "mypage/myComment";
	}
	
	//중고책방 댓글
	@GetMapping(value = "/myProductBoardComment")
	public String myProductBoardComment(HttpSession session,Model model, int reqPage) {
		//멤버 아이디 받아서 select해온걸 넘긴다
		Member m = (Member) session.getAttribute("m");
		MypageListData mld =  mypageService.selectMyProductBoardComment(m, reqPage);

		//서비스에서 제목처럼 이미지 파일패스 추가
		model.addAttribute("myProductBoardCommentList", mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		
		
		return "mypage/myProductBoardComment";
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
	
	//회원탈퇴
	@GetMapping(value = "/delete")
	public String memberDelete(@SessionAttribute(required = false)Member m, Model model) {//로그인로직 내가 안만들었는데 세션멤버m 써도 되는건가? 위에 set어트리뷰트 하긴했는데// 써지긴하네
		System.out.println(m);
		
		int result = mypageService.deleteMember(m.getMemberNo());
		if(result>0) {//회원탈퇴 성공한 경우
			model.addAttribute("title", "회원 탈퇴 성공");
			model.addAttribute("msg", "회원 탈퇴후 로그아웃을 진행합니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/member/logout");//로그아웃해
			return "common/msg";

			
		}else {
			model.addAttribute("title", "회원 탈퇴 실패");
			model.addAttribute("msg", "회원 정보 삭제 실패했습니다.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/mypage/memberUpdateFrm");
		}
		return "common/msg";
		
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
		MypageListData mld =mypageService.selectMySellBook(m, reqPage);

		model.addAttribute("mySellBookList", mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		//model.addAttribute("mySellBookImgList", mld.getMySellBookImgList());
		
		
		return "mypage/mySellBook";
	}
	
	@GetMapping(value = "/mySellBookDelete")
	public String mySellBookDelete(int productBoardNo) {
		System.out.println("MypageController 삭제할 판매도서 번호 : "+productBoardNo);
		
		//int result = mypageService.mySellBookDelete(productBoardNo); //메소드 가져오는게 나을지도?
		
		
		return "redirect:/mypage/mySellBook?reqPage=1";
	}
	@GetMapping(value = "/byRequest")
	public String byRequest(HttpSession session, Model model, Product p ) {
		Member m = (Member)session.getAttribute("m");
		int result = mypageService.tradeInsert(m,p);
		
		//return "mypage/byRequest";
		return "redirect:/product/productDetail?productBoardNo="+p.getProductBoardNo();

	}
	@GetMapping(value = "/showConsumer")
	public String showConsumer (Product p , HttpSession session, int reqPage, Model model) {
		
		System.out.println(p);
		Member m = (Member)session.getAttribute("m");
		System.out.println(m);
		mypageService.selectConsumer(p,m,reqPage);
		MypageListData mld = mypageService.selectConsumer(p,m,reqPage);
			
		model.addAttribute("selectConsumerList",mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		model.addAttribute("product", p);
		
		return"mypage/showConsumer";
	}
	
	@GetMapping(value="/byRequestList")
	public String byRequestList(int reqPage, Model model) {
		
		return "mypage/byRequestList";
	}
	
}
