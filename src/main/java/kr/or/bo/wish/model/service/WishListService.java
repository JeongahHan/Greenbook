package kr.or.bo.wish.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bo.wish.model.dao.WishListDao;

@Service
public class WishListService {
	@Autowired
	private WishListDao wishListDao;
}
