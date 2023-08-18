package kr.or.bo.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bo.member.model.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
}
