package kr.or.bo.msg.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Msg {
	private int mid;
	private String sender;
	private String receiver;
	private String message;
	private int readChk;
	private String sendDate;
}
