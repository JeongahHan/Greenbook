package kr.or.bo.member.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class adminListData {

	private List adminList;
	private String pageNavi;
	//쪽지 기능으로 reqPage 추가
	private int reqPage;
}
