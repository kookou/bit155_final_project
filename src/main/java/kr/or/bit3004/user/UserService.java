package kr.or.bit3004.user;

import java.util.List;


import javax.servlet.http.HttpSession;

public interface UserService {
	public void insertUser(User user);
	public int idCheck(String id);
	public void updateUser(User user, HttpSession session);
	public String updateUserPwd(String pwd, String newPwd, HttpSession session);
	
	public User getUser(String id);
	public List<User> getUserList();
	
	public void deleteUser(String id, HttpSession session);
	
}
