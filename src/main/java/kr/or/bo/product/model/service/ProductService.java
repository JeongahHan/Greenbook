package kr.or.bo.product.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.product.model.dao.ProductDao;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductComment;
import kr.or.bo.product.model.vo.ProductFile;
import kr.or.bo.product.model.vo.ProductListData;
import kr.or.bo.product.model.vo.ProductViewData;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	public int insertPhoto(Product p, ArrayList<ProductFile> fileList) {
		int result = productDao.insertPhoto(p);
		
		if(fileList != null) {
			int productNo = productDao.getProductNo();
			for(ProductFile file : fileList) {
				file.setProductNo(productNo);
				result += productDao.insertPhotoFile(file);
			}
		}
		return result;
	}

	@Transactional
	public ProductListData selectProductList(int reqPage) {
		
		int numPerPage = 15;
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List productList = productDao.selectProductList(start, end);
		for(Object obj : productList) {
			Product p = (Product)obj;
			ProductFile pf = productDao.selectProductImgList(p.getProductBoardNo());
			// p.setFilepath(filepath);
			// p.setProductFile(productFile);
			p.setProductFile(pf);
		}
		
		int totalCount = productDao.selectProductTotalCount();
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		int pageNaviSize = 10;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "<ul class='pagination'>";
		
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/product/board?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		for(int i=0; i<pageNaviSize; i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/product/board?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/product/board?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if(pageNo>totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/product/board?reqPage="+(pageNo)+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		ProductListData pld = new ProductListData(productList, pageNavi);
		
		return pld;
	}

	@Transactional
	public ProductViewData selectOneProduct(int productBoardNo, int memberNo) {
		int result = productDao.updateReadCount(productBoardNo);
		if(result > 0) {
			Product p = productDao.selectOneProduct(productBoardNo);
			ProductFile pf = productDao.selectProductImgList(productBoardNo);
			
			p.setProductFile(pf);
			
			List commentList = productDao.selectCommentList(productBoardNo);
			
			List reCommentList = productDao.selectRecommentList(productBoardNo);
			
			ProductViewData pvd = new ProductViewData(p, commentList, reCommentList);
			
			return pvd;
		}else {
			return null;
		}
	}

	@Transactional
	public int insertComment(ProductComment pc) {
		int result = productDao.insertComment(pc);
		return result;
	}

	public int updateComment(ProductComment pc) {
		int result = productDao.updateComment(pc);
		return result;
	}

	public int deleteComment(int productCommentNo) {
		return productDao.deleteComment(productCommentNo);
	}

	public ProductListData getSearchList(int reqPage, String type, String keyword) {
		
		int numPerPage = 15;
		
		int end = reqPage * numPerPage;
		
		int start = (end - numPerPage) + 1;
		
		List productList = productDao.getSearchList(start, end, type, keyword);
		
		int totalCount = productDao.getSearchListTotalCount(type, keyword);
		
		int totalPage;

		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = (totalCount/numPerPage)+1;
		}
		
		int pageNaviSize = 10;
		
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "<ul class='pagination'>";
		
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/product/getSearchList?reqPage="+(pageNo-1)+"&type="+(type)+"&keyword="+(keyword)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		for(int i=0; i<pageNaviSize; i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/product/getSearchList?reqPage="+(pageNo)+"&type="+(type)+"&keyword="+(keyword)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/product/getSearchList?reqPage="+(pageNo)+"&type="+(type)+"&keyword="+(keyword)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			
			pageNo++;
			
			if(pageNo>totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/product/getSearchList?reqPage="+(pageNo)+"&type="+(type)+"&keyword="+(keyword)+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		ProductListData pld = new ProductListData(productList, pageNavi);
		
		return pld;
	}
	
}
