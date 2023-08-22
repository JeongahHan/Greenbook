package kr.or.bo.msg.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Msg {
	int mid;
	String sender;
	String receiver;
	String message;
	int readChk;
	String sendDate;
}
