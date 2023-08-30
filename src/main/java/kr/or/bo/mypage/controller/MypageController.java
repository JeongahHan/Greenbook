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
import kr.or.bo.mypage.model.vo.TradeList;
import kr.or.bo.product.model.service.ProductService;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductFile;
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
		
		//진행확인용
//		System.out.println("mld : "+mld);
//		System.out.println("넘겨주는거 확인"+mld.getMypageList()); //오브젝트인가? 리스트인가
//		List bc =  mld.getMypageList();
//		System.out.println("리스트인지 확인 : "+bc);//리스트인듯
		
		
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
	public String update(Member member, HttpSession session, Model model) {
		// disabled 때문인거 같은데 멤버 비밀번호, 휴대폰, 이메일만 넘겨받음
		System.out.println(member.getMemberPw());

		int result = mypageService.updateMember(member);
		//로그인되있는 세션 정보 바꿔주기
		//메소드 가져다 쓰기
		if(result >0) {
			Member m = memberService.selectOneMember(member.getMemberId(), member.getMemberPw());
			session.setAttribute("m", m);
			model.addAttribute("title", "정보수정 성공");
			model.addAttribute("msg", "변경된 정보를 확인하세요.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/mypage/memberUpdateFrm");
			return "common/msg";
			
		}else {
			model.addAttribute("title", "정보변경 실패");
			model.addAttribute("msg", "회원 정보 수정에 실패했습니다.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/mypage/memberUpdateFrm");
			return "common/msg";

		}
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
	public String byRequest(HttpSession session, Model model, Product p) {
		Member m = (Member)session.getAttribute("m");
		int result = mypageService.tradeInsert(m, p);
		if(result>0) {
			model.addAttribute("title", "구매요청 성공");
			model.addAttribute("msg", "판매자에게 구매요청이 전송되었습니다.");
			model.addAttribute("icon", "");
		}else {
			model.addAttribute("title", "구매요청 실패");
			model.addAttribute("msg", "관리자에게 문의하여주시기 바랍니다.");
			model.addAttribute("icon", "");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+p.getProductBoardNo());
		return "common/msg";
//		return "mypage/byRequestList";
//		return "redirect:/product/productDetail?productBoardNo="+p.getProductBoardNo();
//		return "redirect:/mypage/byRequestList";
	}
	@GetMapping(value = "/showConsumer")
	public String showConsumer (Product product ,ProductFile productFile, HttpSession session, int reqPage, Model model) {
		
		Member m = (Member)session.getAttribute("m");
		//mypageService.selectConsumer(p,m,reqPage);
		product.setProductFile(productFile);
		//tradeList조회해오기
		MypageListData mld = mypageService.selectConsumer(product,m,reqPage);
		
		
		model.addAttribute("selectConsumerList",mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		model.addAttribute("product", product);
		System.out.println("mld 보기 : "+mld);
		
		return"mypage/showConsumer";
	}
	
	
	@GetMapping(value="/byRequestList")
	public String byRequestList(HttpSession session, int reqPage, Model model) {
		
		Member m = (Member)session.getAttribute("m");
		MypageListData mld = mypageService.selectByRequestList(m.getMemberId(), reqPage);
		
		List byRequestList = productService.selectbyRequestList(m);
//		List tradeList = mypageService.selectTradeList(m);
		
		System.out.println(mld.getMypageList());
		System.out.println(mld.getMypageList().size());
		model.addAttribute("requestList", mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
//		
//		System.out.println(m);
//		System.out.println(p);
//		
//		model.addAttribute("byRequestList", mld.getMypageList());
		
		return "mypage/byRequestList";
	}
	
	//중고책방 상세보기에서 구매요청버튼을 눌럿을때
	@GetMapping(value = "/soldOut")
	public String soldOut(TradeList tradeList, Member member, Model model) {
		System.out.println("컨트롤로 잘 오나");
		tradeList.setMember(member);//멤버 담아온거 셋팅
		System.out.println(tradeList);
		
		int result = mypageService.soldOut(tradeList);
		if(result>0) {
			model.addAttribute("title", "판매완료");
			model.addAttribute("msg", "");
			model.addAttribute("icon", "");
		}else {
			model.addAttribute("title", "판매 실패");
			model.addAttribute("msg", "관리자에게 문의하여주시기 바랍니다.");
			model.addAttribute("icon", "");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+tradeList.getProductBoardNo());

		
		return "common/msg";//임시로 회원정보로
	}
	
}
