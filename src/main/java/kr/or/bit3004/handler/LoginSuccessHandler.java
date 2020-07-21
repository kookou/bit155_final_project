package kr.or.bit3004.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import kr.or.bit3004.user.SessionUser;
import kr.or.bit3004.user.User;
import kr.or.bit3004.user.UserServiceImpl;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	    
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
	
	@Autowired
	private UserServiceImpl service;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	 
		 saveCurrentUserToSession(request, authentication);		 
		 clearAuthenticationAttributes(request);
		 resultRedirectStrategy(request, response, authentication);
		 
		System.out.println("onAuthenticationSuccess");
		 
	}
	
	
    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
            
        } else {
            String defaultUrl = "/";
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }  
    }
    
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    
    protected void saveCurrentUserToSession(HttpServletRequest request, Authentication authentication) {
//    	System.out.println("saveCurrentUsertoSession");
//    	System.out.println("cUserId : " + authentication.getName());
    	
    	SessionUser currentUser = new SessionUser(service.getUser(authentication.getName()));
    	
        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", currentUser);
        session.setMaxInactiveInterval(60*60*1); // 1시간
    }
    
    
	

}
