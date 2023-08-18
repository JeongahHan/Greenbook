package kr.or.bo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {

	public String getFilepath(String savepath, String filename) {
		// filename -> test.txt
		// test            .txt
		String onlyFilename = filename.substring(0, filename.lastIndexOf(".")); // test
		String extention = filename.substring(filename.lastIndexOf(".")); // .txt
		// 실제업로드 할 파일명을 저장할 변수
		String filepath = null;
		// 파일명 중복되면 뒤에 붙일 숫자
		int count = 0;
		while(true) {
			if(count == 0) {
				filepath = onlyFilename+extention; // filename
			}else {
				filepath = onlyFilename+"_"+count+extention; // filename
			}
			// C:/Temp/upload/notice/+filepath
			File checkFile = new File(savepath+filepath);
			if(!checkFile.exists()) {
				break;
			}
			count++;
		}
		return filepath;
	}

	public void downloadFile(String savepath, String filename, String filepath, HttpServletResponse response) {
		String downFile = savepath+filepath;
		
		// 파일을 객체로 읽어오기위한 스트림 생성
		try {
			FileInputStream fis = new FileInputStream(downFile);
			
			// 속도개선을 위한 보조스트림 생성
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			// 읽어온 파일을 사용자에게 내보낼 스트림 생성
			ServletOutputStream sos = response.getOutputStream();
			
			// 속도개선을 위한 보조스트림 생성
			BufferedOutputStream bos = new BufferedOutputStream(sos);
			
			// 파일명 처리
			String resFilename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
			
			// 파일 다운로드를 위한 HTTP 헤더 설정(보내주는 타입이 파일이라는걸 알려주는 것)
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attchment;filename="+resFilename); // 파일 이름은 이거야
			
			// 파일을 읽어온 뒤 전송
			while(true) {
				int data = bis.read();
				if(data != -1) {
					bos.write(data);
				}else {
					break;
				}
			}
			bis.close();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
