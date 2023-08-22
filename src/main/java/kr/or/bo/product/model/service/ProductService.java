package kr.or.bo.product.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.product.model.dao.ProductDao;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductFile;
import kr.or.bo.product.model.vo.ProductListData;

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
	
}
