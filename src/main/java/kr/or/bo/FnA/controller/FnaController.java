package kr.or.bo.FnA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/FnA")
public class FnaController {

	@GetMapping(value = "/FnA")
	public String FnA(){
		return "/FnA/FnA";
	}
}
