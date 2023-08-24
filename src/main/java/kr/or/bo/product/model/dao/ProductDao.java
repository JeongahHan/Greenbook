package kr.or.bo.product.model.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductComment;
import kr.or.bo.product.model.vo.ProductCommentRowMapper;
import kr.or.bo.product.model.vo.ProductFile;
import kr.or.bo.product.model.vo.ProductFileRowMapper;
import kr.or.bo.product.model.vo.ProductRowMapper;

@Repository
public class ProductDao {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private ProductRowMapper productRowmapper;
	
	@Autowired
	private ProductFileRowMapper productFileRowmapper;
	
	@Autowired
	private ProductCommentRowMapper productCommentRowmapper;

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

	public List selectProductList(int start, int end) {
		String query = "SELECT * FROM (SELECT ROWNUM AS RNUM, N.* FROM (SELECT * FROM PRODUCT_BOARD ORDER BY 1 DESC)N) WHERE RNUM BETWEEN ? AND ?";
		List productList = jdbc.query(query,  productRowmapper, start, end);
		return productList;
	}

	public int selectProductTotalCount() {
		String query = "select count(*) as cnt from product_board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public ProductFile selectProductImgList(int productBoardNo) {
		String query = "select * from product_file where product_no=?";
		List list = jdbc.query(query, productFileRowmapper, productBoardNo);
		return (ProductFile)list.get(0);
	}

	public int updateReadCount(int productBoardNo) {
		String query = "UPDATE PRODUCT_BOARD SET READ_COUNT = READ_COUNT+1 WHERE PRODUCT_BOARD_NO = ?";
		Object[] params = {productBoardNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public Product selectOneProduct(int productBoardNo) {
		String query = "select * from product_board where product_board_no = ?";
		List list = jdbc.query(query, productRowmapper, productBoardNo);
		return (Product)list.get(0);
	}

	public List selectProductFile(int productBoardNo) {
		String query = "select * from product_file where product_no = ?";
		List list = jdbc.query(query, productFileRowmapper, productBoardNo);
		return list;
	}

	public List selectCommentList(int productBoardNo) {
		String query = "select * from PRODUCT_COMMENT where PRODUCT_REF = ? and PRODUCT_COMMENT_REF is null order by 1";
		List list = jdbc.query(query, productCommentRowmapper, productBoardNo);
		return list;
	}

	public List selectRecommentList(int productBoardNo) {
		String query = "select * from PRODUCT_COMMENT where PRODUCT_REF = ? and PRODUCT_COMMENT_REF is not null order by 1";
		List list = jdbc.query(query, productCommentRowmapper, productBoardNo);
		return list;
	}

	public int insertComment(ProductComment pc) {
		String query = "INSERT INTO PRODUCT_COMMENT VALUES(PRODUCT_COMMENT_SEQ.NEXTVAL, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), ?, ?)";
		String productCommentRef = pc.getProductCommentRef() == 0 ? null : String.valueOf(pc.getProductCommentRef());
		Object[] params = {pc.getProductCommentWriter(), pc.getProductCommentContent(), pc.getProductRef(), productCommentRef};
		int result = jdbc.update(query, params);
		return result;
	}
	
}
