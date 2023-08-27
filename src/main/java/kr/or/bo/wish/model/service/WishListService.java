package kr.or.bo.wish.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.wish.model.dao.WishListDao;

@Service
public class WishListService {
	@Autowired
	private WishListDao wishListDao;
	
	@Transactional
	public int insertWish(int productBoardNo, String memberId) {
		int result = wishListDao.insertWish(productBoardNo, memberId);
		return result;
	}
	
	@Transactional
	public int deleteWish(int productBoardNo, String memberId) {
		int result = wishListDao.deleteWish(productBoardNo, memberId);
		return result;
	}
}
