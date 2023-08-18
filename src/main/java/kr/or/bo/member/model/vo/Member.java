package kr.or.bo.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberPhone;
	private String memberEmail;
	private int memberLevel;
	private String enrollDate;
	private int grade;
}
