package kr.or.bo.wish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bo.wish.model.service.WishListService;

@Controller
@RequestMapping(value = "/wish")
public class WishListController {
	@Autowired
	private WishListService wishListService;

}
