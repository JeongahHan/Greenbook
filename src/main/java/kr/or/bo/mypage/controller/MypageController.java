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

import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardComment;
import kr.or.bo.board.vo.BoardFile;
import kr.or.bo.member.model.service.MemberService;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.service.MypageService;
import kr.or.bo.mypage.model.vo.MypageListData;
import kr.or.bo.mypage.model.vo.TradeList;
import kr.or.bo.product.model.service.ProductService;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductComment;
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
	
	//나의 독서노트
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
	
	
	//나의 독서노트 삭제
	@GetMapping(value = "/myBoardDelete")
	public String myBoardDelete(Board board, Model model) {
		System.out.println("삭제 진행확인");
		System.out.println(board.getBoardNo());
		int result = mypageService.myBoardDelete(board);
		if(result >0) {
			model.addAttribute("title", "독서노트 삭제성공");
			model.addAttribute("msg", "");
			model.addAttribute("icon", "success");
			
		}else {
			model.addAttribute("title", "독서노트삭제 실패");
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("icon", "error");

		}
		model.addAttribute("loc", "/mypage/myBoard?reqPage=1");
		
		return "common/msg";
		//return "redirect:/mypage/myBoard?reqPage=1";
	}
	
	//독서노트 댓글
	@GetMapping(value = "/myComment")
	public String myComment(HttpSession session,Model model, int reqPage) {
		//내가 작성한 댓글 조회해서 넘겨주기
		Member m = (Member) session.getAttribute("m");
		MypageListData mld = mypageService.selectMyComment(m, reqPage);
		
		//서비스에서 제목받아오기
		model.addAttribute("myBoardCommentList",mld.getMypageList()); //리스트넘김
		model.addAttribute("pageNavi", mld.getPageNavi());
		
		//어떻게 받아지나
		BoardComment bf =  (BoardComment) mld.getMypageList().get(0);
		System.out.println("마이페이지 컨트롤 보드파일  : "+bf.getBoardFile());
		
		
		return "mypage/myComment";
	}
	//독서노트 댓글 삭제
	@GetMapping(value = "/myCommentDelete")
	public String myCommentDelete(BoardComment boardComment, Model model) {
		
		int result = mypageService.myCommentDelete(boardComment);
		if(result >0) {
			model.addAttribute("title", "독서노트 댓글 삭제성공");
			model.addAttribute("msg", "");
			model.addAttribute("icon", "success");
			
		}else {
			model.addAttribute("title", "독서노트삭제 실패");
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("icon", "error");

		}
		model.addAttribute("loc", "/mypage/myComment?reqPage=1");
		
		return "common/msg";
		//return "redirect:/mypage/myComment?reqPage=1";
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
	//중고책방 댓글 삭제
	@GetMapping(value = "/myProductBoardCommentDelete")
	public String myProductBoardCommentDelete(ProductComment productComment , Model model) {
		
		int result =mypageService.myProductBoardCommentDelete(productComment);
		if(result >0) {
			model.addAttribute("title", "중고책방 댓글 삭제성공");
			model.addAttribute("msg", "");
			model.addAttribute("icon", "success");
			
		}else {
			model.addAttribute("title", "중고책방 댓글 삭제 실패");
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("icon", "error");

		}
		model.addAttribute("loc", "/mypage/myProductBoardComment?reqPage=1");
		
		return "common/msg";
		
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
		
		Product p = (Product) mld.getMypageList().get(0);
		System.out.println("마이페이지 서비스 : " + p);
		
		return "mypage/mySellBook";
	}
	
	@GetMapping(value = "/mySellBookDelete")
	public String mySellBookDelete(int productBoardNo) {
		System.out.println("MypageController 삭제할 판매도서 번호 : "+productBoardNo);
		
		//int result = mypageService.mySellBookDelete(productBoardNo); //메소드 가져오는게 나을지도?
		
		
		return "redirect:/mypage/mySellBook?reqPage=1";
	}
	
	//구매요청버튼 클릭시
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
	//구매요청취소 버튼 클릭시
	@GetMapping(value = "/buyRequestCancle")
	public String buyRequestCancle(HttpSession session, Model model,Product p, Member m) {
		
		int result = mypageService.tradeDelete(m,p);
		if(result>0) {
			model.addAttribute("title", "구매요청취소");
			model.addAttribute("msg", "");
			model.addAttribute("icon", "");
		}else {
			model.addAttribute("title", "구매요청취소 실패");
			model.addAttribute("msg", "관리자에게 문의하여주시기 바랍니다.");
			model.addAttribute("icon", "");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+p.getProductBoardNo());
		return "common/msg";
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
		
		return"mypage/showConsumer";
	}
	
	
	@GetMapping(value="/byRequestList")
	public String byRequestList(HttpSession session, int reqPage, Model model) {
		
		Member m = (Member)session.getAttribute("m");
		MypageListData mld = mypageService.selectByRequestList(m.getMemberId(), reqPage);
//		List byRequestList = mypageService.selectByRequestList(m.getMemberId(), reqPage);
		
//		List byRequestList = productService.selectbyRequestList(m);
//		List tradeList = mypageService.selectTradeList(m);
		
//		System.out.println("컨트롤러로 돌아와서 있는 구매현황 리스트 갯수 : "+mld.getMypageList().size());
//		System.out.println(mld.getMypageList());
//		System.out.println("byRequestList : "+byRequestList);
		System.out.println("mld.getMypageList : "+mld.getMypageList());
		
//		model.addAttribute("requestList", byRequestList);
		model.addAttribute("requestList", mld.getMypageList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		model.addAttribute("reqPage", reqPage);
//		
//		System.out.println(m);
//		System.out.println(p);
//		
//		model.addAttribute("byRequestList", mld.getMypageList());
		
		return "mypage/byRequestList";
	}
	
	//중고책방 상세보기에서 구매요청버튼을 눌럿을때//고객정보 보기에서 판매완료 버튼 눌렀을때
	@GetMapping(value = "/soldOut")
	public String soldOut(TradeList tradeList, Member member, Model model) {
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
	
	@GetMapping(value = "/gradeUp")
	public String gradeUp(String writer, String tradeNo) {
		int result = mypageService.gradeUp(writer, tradeNo);
		return "redirect:/mypage/byRequestList?reqPage=1";
	}
	
	@GetMapping(value = "/gradeDown")
	public String gradeDown(String writer, String tradeNo) {
		int result = mypageService.gradeDown(writer, tradeNo);
		return "redirect:/mypage/byRequestList?reqPage=1";
	}
	
}
