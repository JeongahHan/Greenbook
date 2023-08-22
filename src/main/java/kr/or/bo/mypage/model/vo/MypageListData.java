package kr.or.bo.mypage.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MypageListData {
	private List mypageList;
	private String pageNavi;
}
