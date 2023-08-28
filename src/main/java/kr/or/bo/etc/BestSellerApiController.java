package kr.or.bo.etc;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.or.bo.etc.model.vo.BestSeller;

@Controller
@RequestMapping(value="/now")
public class BestSellerApiController {
	
	@GetMapping(value="/best")
	public String map() {
		return "api/bestSeller";
	}
	
	@ResponseBody
	@GetMapping(value="/bestseller")
	public ArrayList<BestSeller> bestseller(){
		
		String url="http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttb-_-hanjeonga1726001&QueryType=Bestseller&&SearchTarget=Book&start=1&MaxResults=20&cover=Big&output=js&inputEncoding=utf-8&Version=20131101";
		
		
		ArrayList<BestSeller> list = new ArrayList<BestSeller>();
		
		try {
			String jsonData  = Jsoup.connect(url).ignoreContentType(true).get().text();
			
		    JsonParser jsonParser = new JsonParser();
		    JsonElement jsonElement = jsonParser.parse(jsonData);
		    JsonObject jsonObject = jsonElement.getAsJsonObject();
		    JsonArray item = jsonObject.get("item").getAsJsonArray();
			
			
			for(int i=0; i<item.size(); i++) {
				JsonObject obj = item.get(i).getAsJsonObject();
				
				String cover = obj.get("cover").getAsString();
				String title = obj.get("title").getAsString();
				String author = obj.get("author").getAsString();
				
				BestSeller b = new BestSeller(cover,title,author);
				list.add(b);
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		

		return list;
		
	}
	
	
	@GetMapping(value="/company")
	public String company() {
		return "company/company";
	}
	
	
	
	
	
}
