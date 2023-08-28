package kr.or.bo.msg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.msg.model.service.MsgService;
import kr.or.bo.msg.model.vo.Msg;
import kr.or.bo.msg.model.vo.MsgListData;

@Controller
@RequestMapping(value = "/msg")
public class MsgController {
	@Autowired
	private MsgService msgService;
	
	//로그인 체크하기
	@GetMapping(value = "/loginCheck")
	public String loginCheck(Model model, @SessionAttribute(required = false) Member m) {
		//로그인이 되지 않은 경우 -> 쪽지리스트 볼 수 없음
		if(m == null) {
			model.addAttribute("title", "접근 권한 없음");
			model.addAttribute("msg", "로그인 후 볼 수 있습니다.");
			model.addAttribute("loc", "/");
			return "common/msg";
		//로그인이 된 경우
		}else {
			return "redirect:/msg/receiveList?reqPage=1";	
		}
	}
	
	//받은 쪽지 리스트
	@GetMapping(value = "/receiveList")
	public String receiveList(Model model, int reqPage, @SessionAttribute(required = false) Member m) {
		MsgListData mld = msgService.selectReceiveList(m.getMemberId(), reqPage);
		model.addAttribute("list", mld.getMsgList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		model.addAttribute("reqPage", reqPage);
		return "msg/receiveMsgList";
	}
	
	//보낸 쪽지 리스트
	@GetMapping(value = "/sendList")
	public String sendList(Model model, int reqPage, @SessionAttribute(required = false) Member m) {
		MsgListData mld = msgService.selectSendList(m.getMemberId(), reqPage);
		model.addAttribute("list", mld.getMsgList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		return "msg/sendMsgList";
	}
	
	//관리자에게 쪽지 보내기
	@PostMapping(value = "/sendMsgToAdmin")
	public String sendMsgToAdmin(Msg msg, Model model) {
		int result = msgService.sendMsgToAdmin(msg);
		//쪽지 전송 성공
		if(result > 0) {
			model.addAttribute("title", "전송 완료");
			model.addAttribute("msg", "관리자에게 쪽지가 성공적으로 전송되었습니다.");
		//쪽지 전송 실패
		}else {
			model.addAttribute("title", "전송 실패");
			model.addAttribute("msg", "관리자에게 쪽지 전송을 실패하였습니다.");
		}
		model.addAttribute("loc", "/msg/receiveList?reqPage=1");
		return "common/msg";
	}
	
	//받은 쪽지 상세 보기
	@ResponseBody
	@GetMapping(value = "/receiveView")
	public Msg receiveView(int mid, Model model) {
		//쪽지 번호로 해당 쪽지 정보 가져오기
		Msg msg = msgService.selectReceiveView(mid);
		//쪽지 번호로 해당 쪽지 열람 여부 바꾸기(미열람 -> 열람)
		int result = msgService.readMsg(mid);
		return msg;
	}
	
	//보낸 쪽지 상세 보기 -> 열람 여부 update하지 않음
	@ResponseBody
	@GetMapping(value = "/sendView")
	public Msg sendView(int mid, Model model) {
		//쪽지 번호로 해당 쪽지 정보 가져오기
		Msg msg = msgService.selectReceiveView(mid);
		return msg;
	}
	
	//쪽지 삭제하기
	@ResponseBody
	@GetMapping(value = "/deleteMsg")
	public void deleteMsg(int mid, Model model) {
		int result = msgService.deleteMsg(mid);
	}
	
	//쪽지 답장하기
	@PostMapping(value = "/replyMsg")
	public String replyMsg(Msg msg, Model model, int reqPage) {
		int result = msgService.replyMsg(msg);
		//쪽지 전송 성공
		if(result > 0) {
			model.addAttribute("title", "전송 완료");
			model.addAttribute("msg", "쪽지가 성공적으로 전송되었습니다.");
		//쪽지 전송 실패
		}else {
			model.addAttribute("title", "전송 실패");
			model.addAttribute("msg", "쪽지 전송에 실패하였습니다.");
		}
		model.addAttribute("loc", "/msg/receiveList?reqPage="+reqPage);
		return "common/msg";
	}
	
	//관리자가 회원에게 쪽지 보내기
	@PostMapping(value = "/adminSendMsg")
	public String adminSendMsg(Msg msg, int reqPage, Model model) {
		int result = msgService.adminSendMsg(msg);
		//쪽지 전송 성공
		if(result > 0) {
			model.addAttribute("title", "전송 완료");
			model.addAttribute("msg", "쪽지가 성공적으로 전송되었습니다.");
		//쪽지 전송 실패
		}else {
			model.addAttribute("title", "전송 실패");
			model.addAttribute("msg", "쪽지 전송에 실패하였습니다.");
		}
		model.addAttribute("loc", "/member/list?reqPage="+reqPage);
		return "common/msg";
	}
	
	//findResult.html에서 쪽지 보내기
	@PostMapping(value = "/findResultSendMsg")
	public String findResultSendMsg(Msg msg, Model model) {
		int result = msgService.findResultSendMsg(msg);
		//쪽지 전송 성공
		if(result > 0) {
			model.addAttribute("title", "전송 완료");
			model.addAttribute("msg", "쪽지가 성공적으로 전송되었습니다.");
		//쪽지 전송 실패
		}else {
			model.addAttribute("title", "전송 실패");
			model.addAttribute("msg", "쪽지 전송에 실패하였습니다.");
		}
		model.addAttribute("loc", "/member/list?reqPage=1");
		return "common/msg";
	}
	
	//중고거래게시판에서 쪽지 보내기
	@PostMapping(value = "/productSendMsg")
	public String productSendMsg(Msg msg, int productRef, Model model) {
		int result = msgService.productSendMsg(msg);
		//쪽지 전송 성공
		if(result > 0) {
			model.addAttribute("title", "전송 완료");
			model.addAttribute("msg", "쪽지가 성공적으로 전송되었습니다.");
		//쪽지 전송 실패
		}else {
			model.addAttribute("title", "전송 실패");
			model.addAttribute("msg", "쪽지 전송에 실패하였습니다.");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+productRef);
		return "common/msg";
	}
	
	//자유게시판에서 쪽지 보내기
	@PostMapping(value = "/boardSendMsg")
	public String boardSendMsg(Msg msg, int boardRef, Model model) {
		int result = msgService.boardSendMsg(msg);
		//쪽지 전송 성공
		if(result > 0) {
			model.addAttribute("title", "전송 완료");
			model.addAttribute("msg", "쪽지가 성공적으로 전송되었습니다.");
		//쪽지 전송 실패
		}else {
			model.addAttribute("title", "전송 실패");
			model.addAttribute("msg", "쪽지 전송에 실패하였습니다.");
		}
		model.addAttribute("loc", "/board/view?boardNo="+boardRef);
		return "common/msg";
	}
	
	//받은 쪽지 중 읽지 않은 쪽지 갯수 구해오기
	@ResponseBody
	@GetMapping(value = "/NotReadMsgCount")
	public int selectNotReadMsgCount(Model model, @SessionAttribute(required = false) Member m) {
		int letterCount = msgService.selectNotReadMsgCount(m.getMemberId());
		return letterCount;
	}
}
