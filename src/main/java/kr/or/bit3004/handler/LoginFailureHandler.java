package kr.or.bit3004.handler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class LoginFailureHandler implements AuthenticationFailureHandler{
	
    private ObjectMapper objectMapper = new ObjectMapper();
	private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
 
    @Override
    public void onAuthenticationFailure(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException exception) 
      throws IOException, ServletException {
    	
    	System.out.println("failure handler");
    	
    	System.out.println(request.getParameter("id"));
      
      request.setAttribute("id", request.getParameter("id"));
      request.setAttribute("pwd", request.getParameter("pwd"));
      System.out.println(exception.getClass());
      
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      
      redirectStratgy.sendRedirect(request, response, "/signin?error=true");

   
    }

}
