package kr.or.bo.mypage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bo.mypage.model.service.MypageService;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {
	@Autowired
	private MypageService mypageService;
	
}
