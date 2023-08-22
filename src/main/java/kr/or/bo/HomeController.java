package kr.or.bo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value="/")
	public String main() {
		return "index";
	}

	@GetMapping(value="/ref")
	public String ref() {
		return "ref";
	}
	
}
