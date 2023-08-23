package kr.or.bo.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductComment {

	private int productCommentNo;
	private String productCommentWriter;
	private String productCommentContent;
	private String productCommentDate;
	private int productRef;
	private int productCommentRef;
	
}
