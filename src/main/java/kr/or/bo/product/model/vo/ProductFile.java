package kr.or.bo.product.model.vo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductFile {

	private int fileNo;
	private int productNo;
	private String filename;
	private String filepath;
	
}
