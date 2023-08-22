package kr.or.bo.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
