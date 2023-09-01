package kr.or.bo;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustom implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		//404 error
		ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/notFound");
		//500 error
		ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/serverError");
		
		factory.addErrorPages(error404, error500);
		
	}

}