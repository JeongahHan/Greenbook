package kr.or.bo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.or.bo.member.model.vo.Member;

public class BlackInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("m");
		if(m.getMemberLevel() == 3) {
			response.sendRedirect("/member/blackMsg");
			return false;
		}else {
			return true;
		}
	}
}
