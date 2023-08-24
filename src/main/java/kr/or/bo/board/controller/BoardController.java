package kr.or.bo.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bo.FileUtil;
import kr.or.bo.board.service.BoardService;
import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardFile;
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
	//로그인한 회원만 댓글 작성가능
	
	@GetMapping(value="/view")
	public String view(int boardNo,@SessionAttribute(required=false) Member m,Model model) { //세션에 있는거 없으면 null 로 주라는 거 넣어놓음,
		//로그인 되어있으면 m에는 로그인한 회원 정보 들어있음 
		//로그인 안되어있으면 m에는 null;
		
		int memberNo = (m==null)? 0:m.getMemberNo();
		
		BoardViewData bvd = boardService.selectOneBoard(boardNo,memberNo);
		
		if(bvd != null) {
			model.addAttribute("b",bvd.getB());  //n : 작성자 작성일 조회수 ~~~
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
///////////////////////////////////////////////////////////////////////////////
//글 작성
	
	@GetMapping(value = "/writeFrm")
	public String boardWriteFrm() {
		return "board/writeFrm"; //페이지이동
	}
	
	@PostMapping (value = "/write",produces="plain/text;charset=utf-8")
	public String boardWrite(Board b,MultipartFile[] upfile,Model model) {  //파일 받을 시 이렇게 ! upfile 은 html 에서 보낸 name 명임
		
		ArrayList<BoardFile> fileList = null; //fileList == null 
		//첨부파일 목록을 저장할 리스트
		if(!upfile[0].isEmpty()) {
			//파일이 없어도 1 (input 때문에) ,  하나여도 1 . 없는건 "첫번째 자리가 비어있으면 이게 진짜 파일이 없는거임"
			fileList = new ArrayList<BoardFile>(); //첨부파일 있는 경우에만 리스트 객체를 생성
			String savepath = root+"boardFile/";
			//파일 업로드는 배열을 반복해줘야함
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();//사용자가 실제로 올린 파일이름
				
				String filepath = fileUtil.getFilepath(savepath, filename); //중복파일명 체크 : 파일경로주고 파일명 줘서 이거보고 체크해서 안겹치게만듬
				
				File uploadFile = new File(savepath+filepath); //실제 폴더에 파일을 업로드
				try {
					file.transferTo(uploadFile);
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//첨부파일 업로드 끝! 
				
				//오류 parent key not found: 부모테이블에 내가 원하는 키가 없다는 뜻 
				BoardFile bf = new BoardFile(); //현재 noticeno > 0임 >> 그래서 0값 넘겨줘서 해당 오류 남
				bf.setFilename(filename);
				bf.setFilepath(filepath);
				fileList.add(bf);
			}
		}
		
		int result = boardService.insertBoard(b,fileList);
		//성공케이스 
		//1.파일이 없으면 result -> 1
		//2. 파일이 있는 경우 result -> 파일개수 +1
		
		if((fileList == null && result==1) || (fileList !=null && result==(fileList.size()+1))) { //성공 조건 
			model.addAttribute("title","게시판 작성 성공");
			model.addAttribute("msg","게시판 작성되었습니다");
		}else {
			model.addAttribute("title","게시판 작성 실패");
			model.addAttribute("msg","에러찾으세요...");
		}
		model.addAttribute("loc","/board/list?reqPage=1");
		return "common/msg";
	}
	
	
	//에디터 부분 (썸머노트)
	
	@ResponseBody
	@PostMapping(value ="/editor",produces="plain/text;charset=utf-8")
	public String editorUpload(MultipartFile file) {  //"file"이랑 맞춰줌
		
		String savepath = root+"boardEditor/"; //새폴더 만들어서 거기다가 업로드
		String filepath = fileUtil.getFilepath(savepath, file.getOriginalFilename());
		File image = new File(savepath+filepath);
		
		try {
			file.transferTo(image);  //파일 업로드. 
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//이 위치에 올라갔다라는것을 알려주기위해 이렇게 리턴 
		return "/boardEditor/"+filepath;  
	}
	
	
	
	
	
//////////////////////////////////////////////////////////////////
	
	
	
	/*
	@GetMapping(value="/filedown")
	public void filedown(NoticeFile file, HttpServletResponse response) {  
		//페이지 이동을 안할꺼기때문에 (html) , return 없음 
		//HttpServletResponse >> 응답에 관련된 객체 : 파일을 다운받기위해 이렇게 매개변수 지정
		
		String savepath = root+"notice/"; //파일을 다운받는 경로(=업로드한 경로와 같아야함)
		fileUtil.downloadFile(savepath,file.getFilename(),file.getFilepath(),response);
	}
	
	*/
	
	
	
	
	
} //컨트롤러 종료