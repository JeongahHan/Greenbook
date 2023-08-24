package kr.or.bo.msg.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MsgListData {
	private List msgList;
	private String pageNavi;
}
