package kr.or.bo.product.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.product.model.dao.ProductDao;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductFile;

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
	
}
