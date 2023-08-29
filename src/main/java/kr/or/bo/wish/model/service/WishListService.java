package kr.or.bo.wish.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.msg.model.vo.MsgListData;
import kr.or.bo.wish.model.dao.WishListDao;
import kr.or.bo.wish.model.vo.WishListData;

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

	public WishListData selectmyWishList(String memberId, int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List myWishList = wishListDao.selectMyWishList(start, end, memberId);
		
		int totalCount = wishListDao.selectMyWishListTotalCount(memberId);
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;

		String pageNavi = "<ul class='pagination'>";				
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/wish/myWishList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		//페이지 숫자
		for(int i=0;i<pageNaviSize;i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/wish/myWishList?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/wish/myWishList?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if(pageNo>totalPage) {
				break;
			}
		}
		//다음 버튼
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/wish/myWishList?reqPage="+(pageNo)+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		WishListData wld = new WishListData(myWishList, pageNavi);
		return wld;
	}
	
	public int totalCount() {
		int totalCount = wishListDao.totalCount();
		return totalCount;
	}

	public List selectWishlist(int start, int end) {
		List list = wishListDao.selectWishlist(start,end);
		return list;
	}
}
