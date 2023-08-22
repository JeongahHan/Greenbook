package kr.or.bo.msg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.msg.model.service.MsgService;
import kr.or.bo.msg.model.vo.Msg;

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
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/");
			return "common/msg";
		//로그인이 된 경우
		}else {
			return "redirect:/msg/receiveList";
		}
	}
	
	//쪽지 리스트
	@GetMapping(value = "/receiveList")
	public String receiveList(Model model, @SessionAttribute(required = false) Member m) {
		List list = msgService.selectReceiveList(m.getMemberId());
		model.addAttribute("list", list);
		System.out.println(list);
		return "msg/msglist";
	}
	
	
}
