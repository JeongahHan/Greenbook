package kr.or.bo.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bo.board.service.BoardService;
import kr.or.bo.board.vo.BoardListData;


@Controller
@RequestMapping(value="/board") 
public class BoardController {
	@Autowired
	private BoardService boardService;

	
	
	
	
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
	
	//제목:내용 찾기

	@GetMapping(value="/getSearchList")
	public String getSearchList(int reqPage,String type,String keyword,Model model) {
		
		BoardListData bld = boardService.getSearchList(reqPage,type,keyword);
		
		model.addAttribute("boardList",bld.getBoardList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		
		return "board/boardSearchList";
	}
	//////////////////////////////////////////////////////////////////

	
	
} //컨트롤러 종료