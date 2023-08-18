package kr.or.bo.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bo.product.model.service.ProductService;

@Controller
@RequestMapping(value="/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping(value="/board")
	public String board() {
		return "product/productBoard";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "product/writeFrm";
	}
	
}
