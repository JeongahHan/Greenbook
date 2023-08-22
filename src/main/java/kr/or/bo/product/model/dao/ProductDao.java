package kr.or.bo.product.model.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductFile;
import kr.or.bo.product.model.vo.ProductRowMapper;

@Repository
public class ProductDao {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private ProductRowMapper productRowmapper;

	public int insertPhoto(Product p) {
		String query = "INSERT INTO PRODUCT_BOARD VALUES (PRODUCT_BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), ?, ?, ?, ?, DEFAULT)";
		Object[] params = {p.getProductBoardTitle(), p.getProductBoardContent(), p.getProductBoardWriter(), p.getProductPrice(), p.getProductAuthor(), p.getProductCondition(), p.getProductSellCheck()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getProductNo() {
		String query = "select max(product_board_no) from product_board";
		int productNo = jdbc.queryForObject(query, Integer.class);
		return productNo;
	}

	public int insertPhotoFile(ProductFile file) {
		String query = "insert into PRODUCT_FILE values(PRODUCT_FILE_SEQ.nextval, ?, ?, ?)";
		Object[] params = {file.getProductNo(), file.getFilename(), file.getFilepath()};
		int result = jdbc.update(query, params);
		return result;
	}
	
}
