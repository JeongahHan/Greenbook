package kr.or.bo.product.controller;

import java.io.File;
import java.io.IOException;

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
	
	@PostMapping(value="/write")
	public String write(Product p, MultipartFile imageFile, Model model) {
		String savepath = root+"product/";
		String filepath = fileUtil.getFilepath(savepath, imageFile.getOriginalFilename());
		p.setFilepath(filepath);
		File upfile = new File(savepath+filepath);
		
		try {
			imageFile.transferTo(upfile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = productService.insertPhoto(p);
		if(result > 0) {
			model.addAttribute("title", "작성완료");
			model.addAttribute("msg", "게시글 작성이 완료되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "작성실패");
			model.addAttribute("msg", "게시글 작성 중 문제가 발생했습니다.");
			model.addAttribute("icon", "error");
		}
		model.addAttribute("loc", "/product/board");
		return "common/msg";
	}
	
}
