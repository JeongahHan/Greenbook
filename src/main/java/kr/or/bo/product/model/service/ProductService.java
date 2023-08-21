package kr.or.bo.product.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.product.model.dao.ProductDao;
import kr.or.bo.product.model.vo.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	public int insertPhoto(Product p) {
		int result = productDao.insertPhoto(p);
		return result;
	}
	
}
