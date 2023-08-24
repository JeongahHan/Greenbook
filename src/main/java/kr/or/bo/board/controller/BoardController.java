package kr.or.bo.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.bo.FileUtil;
import kr.or.bo.board.service.BoardService;
import kr.or.bo.board.vo.BoardListData;
import kr.or.bo.board.vo.BoardViewData;
import kr.or.bo.member.model.vo.Member;


@Controller
@RequestMapping(value="/board") 
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Value("${file.root}")  //따로 application에 빼두고 이렇게 들고오는 이유눈 여기에 바로 하면 경로 넣으면 , 보안상의 문제가됨. 
	private String root;
	@Autowired
	private FileUtil fileUtil;
	
	
	
	
	//리스트
	@GetMapping(value="/list")
	public String boardList(Model model,int reqPage) {
		BoardListData bld = boardService.selectBoardList(reqPage);
		//화면에 출력하기위해 model
		model.addAttribute("boardList",bld.getBoardList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		
		return "board/boardList";
	}

	////////////////////////////////////////////////////////////////////////////
	
	//제목:내용   찾기 기능

	@GetMapping(value="/getSearchList")
	public String getSearchList(int reqPage,String type,String keyword,Model model) {
		
		BoardListData bld = boardService.getSearchList(reqPage,type,keyword);
		
		model.addAttribute("boardList",bld.getBoardList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		
		return "board/boardList";
	}
	//////////////////////////////////////////////////////////////////

	//자유게시판 글 상세보기
	
	//로그인한 회원만 글 작성가능. //로그인 안한회원은 볼수만 있음
	@GetMapping(value="/view")
	public String view(int boardNo,@SessionAttribute(required=false) Member m,Model model) { //세션에 있는거 없으면 null 로 주라는 거 넣어놓음,
		//로그인 되어있으면 m에는 로그인한 회원 정보 들어있음 
		//로그인 안되어있으면 m에는 null;
		
		int memberNo = (m==null)? 0:m.getMemberNo();
		
		BoardViewData bvd = boardService.selectOneBoard(boardNo,memberNo);
		
		if(bvd != null) {
			model.addAttribute("n",bvd.getB());  //n : 작성자 작성일 조회수 ~~~
			model.addAttribute("commentList",bvd.getCommentList());
			model.addAttribute("reCommentList",bvd.getReCommentList());
			
			return "board/boardView";
		}else {
			model.addAttribute("title","조회실패");
			model.addAttribute("msg","이미 삭제된 게시물 입니다.");
			model.addAttribute("icon","info");
			model.addAttribute("loc","/board/list?reqPage=1");
			return "common/msg";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
} //컨트롤러 종료