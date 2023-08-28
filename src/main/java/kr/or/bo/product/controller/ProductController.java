package kr.or.bo.product.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bo.FileUtil;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.product.model.service.ProductService;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductComment;
import kr.or.bo.product.model.vo.ProductFile;
import kr.or.bo.product.model.vo.ProductListData;
import kr.or.bo.product.model.vo.ProductViewData;

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
	
	@GetMapping("/productDetail")
	public String productDetail(int productBoardNo, @SessionAttribute(required = false) Member m, Model model) {
		int memberNo = (m == null) ? 0 : m.getMemberNo();
		ProductViewData pvd = productService.selectOneProduct(productBoardNo, memberNo);
		System.out.println(pvd);
		if(pvd != null) {
			model.addAttribute("p", pvd.getP());
			model.addAttribute("commentList", pvd.getCommentList());
			model.addAttribute("reCommentList", pvd.getReCommentList());
			model.addAttribute("m", pvd.getM());
			// model.addAttribute("fileList", pvd.getFileList());
			return "product/productDetail";
		}else {
			model.addAttribute("title", "조회 실패");
			model.addAttribute("msg", "이미 삭제된 게시물입니다.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/product/board?reqPage=1");
			return "common/msg";
		}
	}
	
	@GetMapping(value="/updateFrm")
	public String updateFrm(int productBoardNo, Model model) {
		Product p = productService.getProduct(productBoardNo);
		model.addAttribute("p", p);
		return "product/productUpdateFrm";
	}
	
	@PostMapping(value="/update")
	public String update(Product p, MultipartFile imageFile, int[] delFileNo, Model model) {
		
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
		pf.setProductNo(p.getProductBoardNo());
		fileList.add(pf);
		
		List list = productService.updateProduct(p, fileList, delFileNo);
		if(list == null) {
			model.addAttribute("title", "수정 실패");
			model.addAttribute("msg", "게시글 수정 중 문제가 발생했습니다.");
			model.addAttribute("icon", "");
		}else {
			for(Object item : list) {
				ProductFile file = (ProductFile)item;
				File delFile = new File(savepath+file.getFilepath());
				delFile.delete();
			}
			model.addAttribute("title", "수정 완료");
			model.addAttribute("msg", "게시글이 수정되었습니다.");
			model.addAttribute("icon", "");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+p.getProductBoardNo());
		return "common/msg";
	}
	
	@GetMapping(value="/delete")
	public String deleteProduct(int productBoardNo, Model model) {
		List list = productService.deleteProduct(productBoardNo);
		if(list != null) {
			String savepath = root+"product/";
			for(Object obj : list) {
				ProductFile file = (ProductFile)obj;
				File delFile = new File(savepath+file.getFilepath());
				delFile.delete();
			}
			model.addAttribute("title", "삭제 완료");
			model.addAttribute("msg", "게시물이 삭제되었습니다.");
			model.addAttribute("loc", "/product/board?reqPage=1");
		}else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "게시물 삭제에 실패했습니다.");
			model.addAttribute("loc", "/product/productDetail?productBoardNo="+productBoardNo);
		}
		return "common/msg";
	}
	
	@PostMapping(value="/insertComment")
	public String insertComment(ProductComment pc, Model model) {
		int result = productService.insertComment(pc);
		if(result > 0) {
			model.addAttribute("title", "등록 완료");
			model.addAttribute("msg", "댓글이 등록되었습니다.");
			model.addAttribute("icon", "");
		}else {
			model.addAttribute("title", "등록 실패");
			model.addAttribute("msg", "댓글 등록에 실패하였습니다");
			model.addAttribute("icon", "");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+pc.getProductRef());
		return "common/msg";
	}
	
	@PostMapping(value="/updateComment")
	public String updateComment(ProductComment pc, Model model) {
		int result = productService.updateComment(pc);
		if(result > 0) {
			model.addAttribute("title", "수정 완료");
			model.addAttribute("msg", "댓글이 수정되었습니다.");
			model.addAttribute("icon", "");
		}else {
			model.addAttribute("title", "수정 실패");
			model.addAttribute("msg", "댓글이 수정에 실패했습니다.");
			model.addAttribute("icon", "");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+pc.getProductRef());
		return "common/msg";
	}
	
	@GetMapping(value="/deleteComment")
	public String deleteComment(int productCommentNo, int productBoardNo, Model model) {
		int result = productService.deleteComment(productCommentNo);
		if(result > 0) {
			model.addAttribute("title", "삭제 완료");
			model.addAttribute("msg", "댓글이 삭제되었습니다.");
			model.addAttribute("icon", "");
		}else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "댓글 삭제에 실패했습니다.");
			model.addAttribute("icon", "");
		}
		model.addAttribute("loc", "/product/productDetail?productBoardNo="+productBoardNo);
		return "common/msg";
	}
	
	@GetMapping(value="/getSearchList")
	public String getSearchList(int reqPage, String type, String keyword, Model model) {
		ProductListData pld = productService.getSearchList(reqPage, type, keyword);
		
//		model.addAttribute("p", pvd.getP());
		model.addAttribute("productList", pld.getProductList());
		model.addAttribute("pageNavi", pld.getPageNavi());
		
		return "product/productBoard";
	}
	
}
