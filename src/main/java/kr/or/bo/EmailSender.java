package kr.or.bo;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	@Autowired
	private JavaMailSender sender;
	
	public String authMail(String memberEmail) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		StringBuffer sb = new StringBuffer();
		
		Random r = new Random();
		
		for(int i=0; i<8; i++) {
			// 0 ~ 9 : r.nextInt(10); //0부터 매개변수 갯수 중 랜덤 수 1개
			// A ~ Z : (char)(r.nextInt(26)+65);
			// a ~ z : (char)(r.nextInt(26)+97);
			int flag = r.nextInt(3); //0:숫자 / 1:대문자 / 2:소문자
			if(flag == 0) {
				int num = r.nextInt(10);
				sb.append(num);
			}else if(flag == 1) {
				char ch = (char)(r.nextInt(26)+65);
				sb.append(ch);
			}else if(flag == 2) {
				char ch = (char)(r.nextInt(26)+97);
				sb.append(ch);
			}
		}
		try {
			helper.setSentDate(new Date());
			helper.setFrom(new InternetAddress("khsgsong@gmail.com", "그린북 관리자"));
			helper.setTo(memberEmail);
			helper.setSubject("GreenBook 인증 메일입니다.");
			helper.setText("<h1>안녕하세요 GreenBook(그린북) 입니다.</h1>"
							+"<h3>인증번호는 [<span style='color:red;'>"+sb.toString()+"</span>] 입니다.</h3>"
							, true);
			sender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
