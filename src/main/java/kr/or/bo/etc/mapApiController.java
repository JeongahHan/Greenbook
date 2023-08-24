package kr.or.bo.etc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api")
public class mapApiController {
	@GetMapping(value="/map")
	public String map() {
		return "api/map";
	}

}
