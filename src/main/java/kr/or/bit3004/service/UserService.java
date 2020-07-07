package kr.or.bit3004.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.or.bit3004.dto.User;

public interface UserService {
	public void insertUser(User user);
	public void idCheck(String id);
//	public User loginCheck(String id, String pwd);
	public void updateUser(User user);
	public void updateUserPwd(String id, String pwd);
	
	public User getUser(String id);
	public List<User> getUserList();
	
	public void deleteUser(String id);
	
}
