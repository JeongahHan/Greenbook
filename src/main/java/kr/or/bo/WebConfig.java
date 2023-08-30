package kr.or.bo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/", "classpath:/static/");
		registry.addResourceHandler("/product/**").addResourceLocations("file:///C:/Temp/upload/product/");
		registry.addResourceHandler("/wish/**").addResourceLocations("classpath:/static/img/product-img/");
		registry.addResourceHandler("/editor/**").addResourceLocations("file:///C:/Temp/upload/editor/");
	 
		
		registry.addResourceHandler("/boardEditor/**").addResourceLocations("file:///C:/Temp/upload/boardEditor/"); //내가 boardEditor 라는 게시판을 쓰게되면 여깄는 자원 가져다 쓰라고 선언해줌
		registry.addResourceHandler("/boardFile/**").addResourceLocations("file:///C:/Temp/upload/boardFile/"); 
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
		.addPathPatterns("/member/logout", "/member/list", "/member/changeLevel", "/member/checkedchangeLevel", "/member/find", "/member/findresult", "/member/levelSearchList",
						"/msg/**", "/wish/**", "/mypage/**")
		.excludePathPatterns("/wish/list", "/wish/more");
		
		registry.addInterceptor(new AdminInterceptor())
		.addPathPatterns("/member/list", "/member/changeLevel", "/member/checkedchangeLevel", "/member/find", "/member/findresult", "/member/levelSearchList");
		
		registry.addInterceptor(new BlackInterceptor())
		.addPathPatterns("/wish/**","/mypage/**")
		.excludePathPatterns("/wish/list", "/wish/more");
	}
}
