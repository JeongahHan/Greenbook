package kr.or.bo.product.model.vo;

import java.util.List;

import kr.or.bo.member.model.vo.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductViewData {

	private Product p;
	private List commentList;
	private List reCommentList;
	private Member m;
	
}
