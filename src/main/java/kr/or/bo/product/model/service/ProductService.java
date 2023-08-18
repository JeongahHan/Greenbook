package kr.or.bo.product.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bo.product.model.dao.ProductDao;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
}
