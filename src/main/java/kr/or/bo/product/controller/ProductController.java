package kr.or.bo.product.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bo.FileUtil;
import kr.or.bo.product.model.service.ProductService;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductFile;
import kr.or.bo.product.model.vo.ProductListData;

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
	public String board(Model model, int reqPage) {
		ProductListData pld = productService.selectProductList(reqPage);
		model.addAttribute("productList", pld.getProductList());
		model.addAttribute("pageNavi", pld.getPageNavi());
		return "product/productBoard";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "product/writeFrm";
	}
	
	@PostMapping(value="/write")
	public String write(Product p, MultipartFile imageFile, Model model) {

		ArrayList<ProductFile> fileList = null;
		if(imageFile != null) {
			fileList = new ArrayList<ProductFile>();
		}
		
		String savepath = root+"product/";
		String filename = imageFile.getOriginalFilename();
		String filepath = fileUtil.getFilepath(savepath, imageFile.getOriginalFilename());
	
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
		
		ProductFile pf = new ProductFile();
		pf.setFilename(filename);
		pf.setFilepath(filepath);
		fileList.add(pf);
		
		int result = productService.insertPhoto(p, fileList);
		if(result > 0) {
			model.addAttribute("title", "작성완료");
			model.addAttribute("msg", "게시글 작성이 완료되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "작성실패");
			model.addAttribute("msg", "게시글 작성 중 문제가 발생했습니다.");
			model.addAttribute("icon", "error");
		}
		model.addAttribute("loc", "/product/board?reqPage=1");
		return "common/msg";
	}
	
	@ResponseBody
	@PostMapping(value="/editor", produces = "plain/text;charset=utf-8")
	public String editorUpload(MultipartFile file) {
		String savepath = root+"editor/";
		String filepath = fileUtil.getFilepath(savepath, file.getOriginalFilename());
		File image = new File(savepath+filepath);
		
		try {
			file.transferTo(image);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/editor/"+filepath;
	}
	
}
