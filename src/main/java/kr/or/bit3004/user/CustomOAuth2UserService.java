package kr.or.bit3004.user;
import org.springframework.core.ParameterizedTypeReference; 
import org.springframework.core.convert.converter.Converter; 
import org.springframework.http.RequestEntity; 
import org.springframework.http.ResponseEntity; 
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler; 
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService; 
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest; 
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter; 
import org.springframework.security.oauth2.core.OAuth2AccessToken; 
import org.springframework.security.oauth2.core.OAuth2AuthenticationException; 
import org.springframework.security.oauth2.core.OAuth2AuthorizationException; 
import org.springframework.security.oauth2.core.OAuth2Error; 
import org.springframework.security.oauth2.core.user.DefaultOAuth2User; 
import org.springframework.security.oauth2.core.user.OAuth2User; 
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert; 
import org.springframework.util.StringUtils; 
import org.springframework.web.client.RestClientException; 
import org.springframework.web.client.RestOperations; 
import org.springframework.web.client.RestTemplate;

import kr.or.bit3004.dao.UserDao;

import java.util.HashMap;
import java.util.LinkedHashMap; 
import java.util.LinkedHashSet; 
import java.util.Map; 
import java.util.Set;

import javax.servlet.http.HttpSession; 


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService { 
	
	private final UserDao userDao;
	private final HttpSession session;
	
	private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri"; 
	private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute"; 
	private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response"; 
	private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = 
			new ParameterizedTypeReference<Map<String, Object>>() {}; 
			
	private Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter(); 
	private RestOperations restOperations; 

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	public CustomOAuth2UserService(UserDao userDao, HttpSession session) {
		this.session= session;
		this.userDao = userDao;
		RestTemplate restTemplate = new RestTemplate(); 
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler()); 
		this.restOperations = restTemplate;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
	} 
	
	@Override 
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException { 
		System.out.println("loadUser");
		
		Assert.notNull(userRequest, "userRequest cannot be null"); 
		
		if (!StringUtils.hasText(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())) { 
			OAuth2Error oauth2Error = new OAuth2Error( MISSING_USER_INFO_URI_ERROR_CODE, 
					"Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " 
					+ userRequest.getClientRegistration().getRegistrationId(), null ); 
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString()); 
			} 
		
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails() .getUserInfoEndpoint().getUserNameAttributeName();
		
//		System.out.println("CustomOAuth2UserService loadUser");
//		System.out.println("userNameAttributeName : " + userNameAttributeName.toString());
		
		if (!StringUtils.hasText(userNameAttributeName)) { 
			OAuth2Error oauth2Error = new OAuth2Error( MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE, 
					"Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: " 
					+ userRequest.getClientRegistration().getRegistrationId(), null ); 
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString()); 	
			} 
		
		RequestEntity<?> request = this.requestEntityConverter.convert(userRequest); 
		ResponseEntity<Map<String, Object>> response; 
		
		try { 
			response = this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE); 
			
		} catch (OAuth2AuthorizationException ex) { 
			OAuth2Error oauth2Error = ex.getError(); 
			StringBuilder errorDetails = new StringBuilder(); 
			errorDetails.append("Error details: [");
			errorDetails.append("UserInfo Uri: ").append( userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri()); 
			errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode()); 
			
			if (oauth2Error.getDescription() != null) { 
				errorDetails.append(", Error Description: ").append(oauth2Error.getDescription()); 	
			} errorDetails.append("]"); 
			
			oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE, 
					"An error occurred while attempting to retrieve the UserInfo Resource: " 
			+ errorDetails.toString(), null); 
			
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex); 
			
		} catch (RestClientException ex) { 
			OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE, 
					"An error occurred while attempting to retrieve the UserInfo Resource: " 
					+ ex.getMessage(), null); 
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex); 
			
		} 
		
		Map<String, Object> userAttributes = getUserAttributes(response); 
		
		System.out.println("userAttributes : "+userAttributes.toString()); // 여기여기 !! 여기가 user정보 !!!
		System.out.println(userAttributes.get("email"));
		
		
		//여기서 해당 email이 우리 db에 있는지 확인하고, 있다면 session에 값을 저장하고 role 부여.
		//없다면 DB에 정보 등록(email 정보가 없다면 email 인증 페이지로 이동)
		
		User user = saveOrUpdate(userAttributes); // 등록 여부 확인 및 DB에 정보등록.
		session.setAttribute("currentUser", new SessionUser(user)); // session에 등록
		
		
		Set<GrantedAuthority> authorities = new LinkedHashSet<>(); 
		authorities.add(new OAuth2UserAuthority(userAttributes));  //OAuth2UserAuthority가 role_user권한을 부여.
		OAuth2AccessToken token = userRequest.getAccessToken(); 
		System.out.println(token.getScopes());
		
		
		for (String authority : token.getScopes()) { 
			authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority)); 	
		} 
		
		System.out.println("authorities : "+ authorities.toString());
		System.out.println("userAttributes : "+userAttributes);
		System.out.println("userNameAttributeName : "+userNameAttributeName);
		
		return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName); 
		
	}

	
	
	// 네이버는 HTTP response body에 response 안에 id 값을 포함한 유저정보를 넣어주므로 유저정보를 빼내기 위한 작업을 함
	private Map<String, Object> getUserAttributes(ResponseEntity<Map<String, Object>> response) { 
		System.out.println("getUserAttributes");
		
		Map<String, Object> userAttributes = response.getBody(); 
		
		if(userAttributes.containsKey("response")) { 
			LinkedHashMap responseData = (LinkedHashMap)userAttributes.get("response"); 
			userAttributes.putAll(responseData); 
			userAttributes.remove("response"); 	
		} 
		
		
		//토큰으로 받아온 유저정보를 콘솔에 출력
		System.out.println(userAttributes.toString());
		return userAttributes; 
	}
	
	
    private User saveOrUpdate(Map<String, Object> userAttributes) {

    	
    	String emailKey = "email";
    	String idKey = "id";
    	String nicknameKey = "name";
    	String imageKey = "";
    	
    	String email = "";
    	String pwd = "";
    	String nickname = "";
    	String image = "";
    	
    	
    	boolean isFB = false;
    	
    	
    	if(userAttributes.get("sub") != null) { // 구글 소셜로그인
    		System.out.println("google");
    		idKey = "sub";
    		imageKey = "picture";
    		
    	}else if(userAttributes.get("nickname") != null) { // 네이버 소셜로그인
    		System.out.println("naver");
    		nicknameKey = "nickname";
    		imageKey = "profile_image";
    		
    	}else { // 페이스북 소셜로그인
    		System.out.println("FB");
    		isFB = true;
    	}
    	
    	email = (String)userAttributes.get(emailKey);
    	pwd = (String)userAttributes.get(idKey);
    	nickname = (String)userAttributes.get(nicknameKey);
    	image = (String)userAttributes.get(imageKey); // null 이 출력되면 null값이 들어갈까?
    	

    	if(isFB && ((userAttributes.get("picture")) != null)) {
    		System.out.println("isFB TRUE");
    		image = "https://graph.facebook.com/"
    				+ pwd
    				+"/picture?type=normal&redirect=true&width=300&height=300";
    	}
    	
    	
//    	
//    	String googleTypeId = (String)userAttributes.get("sub"); // G
//    	String otherTypeId = (String)userAttributes.get("id");  // F,N    	
//    	String naverTypeNickname = (String)userAttributes.get("nickname"); //G,F>> name   naver >> nickname
//    	
//    	
//    	String email = (String)userAttributes.get("email"); // G,F,N 공통
//    	String nickname = (naverTypeNickname != null) ? naverTypeNickname : (String)userAttributes.get("name"); 
//    	String image = (String)userAttributes.get("picture"); // naver :profile_image / google : picture  / facebook : 
//    	
//    	// 사용자 고유id값을 암호화하여 pwd로 사용할 예정
//    	String pwd = (googleTypeId != null) ? googleTypeId : otherTypeId; 
//    	
//    	System.out.println("email :"+email);
//    	System.out.println("pwd : "+pwd);
//    	System.out.println("nickname : "+nickname);

    	User user = new User();
    	
   	
    		System.out.println("email : "+email);
    		System.out.println("pwd : "+pwd);    		
    		System.out.println("nickname : "+nickname);
    		System.out.println("image : "+image);  	
    		
    		int result = userDao.idCheck(email);
            
    		if(result > 0) {
    			user = userDao.getUser(email);
    			
    			System.out.println("user : "+user.toString());
    			System.out.println("등록된 사용자입니다");
    			
    		}else {
    			System.out.println("등록되지 않은 사용자입니다");
    			user.setEnable(1);
    			user.setId(email);
    			user.setNickname(nickname);
    			user.setPwd(bCryptPasswordEncoder.encode(pwd));
    			
    			if(image != null) {
    				System.out.println("이미지 있음");
    				user.setImage(image);
    			}
    			
    			result = userDao.insertUser(user);
    			System.out.println(result);
    			
    			if(result == 1) {
    				System.out.println("사용자 등록이 완료되었습니다.");
    			}

    			user = userDao.getUser(email);
    		}
    	
    	
    	return user;
    	

       
    }
    
}
