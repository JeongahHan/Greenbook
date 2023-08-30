package kr.or.bo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.or.bo.member.model.vo.Member;


public class AdminInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("m");
		System.out.println(m);
		if(m.getMemberLevel() == 1) {
			return true;
		}else {
			response.sendRedirect("/member/adminMsg");
			return false;
		}
	}
	
}
