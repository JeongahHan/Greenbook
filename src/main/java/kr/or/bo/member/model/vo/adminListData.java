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
	private int reqPage;
}
