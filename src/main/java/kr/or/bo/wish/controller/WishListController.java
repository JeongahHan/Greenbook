package kr.or.bo.wish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.msg.model.vo.Msg;
import kr.or.bo.wish.model.service.WishListService;
import kr.or.bo.wish.model.vo.WishListData;

@Controller
@RequestMapping(value = "/wish")
public class WishListController {
	@Autowired
	private WishListService wishListService;
	
	//관심 도서 등록
	@ResponseBody
	@GetMapping(value = "/insertWish")
	public int insertWish(int productBoardNo, @SessionAttribute(required = false) Member m, Model model) {
		int result = wishListService.insertWish(productBoardNo, m.getMemberId());
		return result;
	}
	
	//관심 도서 해제
	@ResponseBody
	@GetMapping(value = "/deleteWish")
	public int deleteWish(int productBoardNo, @SessionAttribute(required = false) Member m, Model model) {
		int result = wishListService.deleteWish(productBoardNo, m.getMemberId());
		return result;
	}
	
	//관심 도서 불러오기
	@GetMapping(value = "/myWishList")
	public String myWishList(Model model, int reqPage, @SessionAttribute(required = false) Member m) {
		WishListData wld = wishListService.selectmyWishList(m.getMemberId(), reqPage);
		model.addAttribute("list", wld.getMyWishList());
		model.addAttribute("pageNavi", wld.getPageNavi());
		return "mypage/myWishList";
	}
}