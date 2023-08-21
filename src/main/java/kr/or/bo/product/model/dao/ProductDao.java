package kr.or.bo.product.model.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductRowMapper;

@Repository
public class ProductDao {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private ProductRowMapper productRowmapper;

	public int insertPhoto(Product p) {
		String query = "INSERT INTO PRODUCT_BOARD VALUES (PRODUCT_BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), ?, ?, ?, 0, 0, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), ?)";
		return 0;
	}
	
}
