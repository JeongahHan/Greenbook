package kr.or.bo.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bo.FileUtil;
import kr.or.bo.product.model.service.ProductService;
import kr.or.bo.product.model.vo.Product;

@Controller
@RequestMapping(value="/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
	
	@GetMapping(value="/board")
	public String board() {
		return "product/productBoard";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "product/writeFrm";
	}
	
	@PostMapping(value="/wirte")
	public String write(Product p, MultipartFile imageFile, Model model) {
		String savepath = root+"product/";
		String filepath = fileUtil.getFilepath(savepath, imageFile.getOriginalFilename());
		
		return root;
		
	}
	
}
