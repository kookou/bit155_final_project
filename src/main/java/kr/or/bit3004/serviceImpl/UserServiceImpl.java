package kr.or.bit3004.serviceImpl;

import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.UserDao;
import kr.or.bit3004.dto.User;
import kr.or.bit3004.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao dao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public void insertUser(User user) {
		System.out.println("dao");
		
		user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
		dao.insertUser(user);	
	}

	@Override
	public void idCheck(String id) {
		dao.idCheck(id);
		
	}

//	@Override
//	public User loginCheck(String id, String pwd) {
//		System.out.println("serviceImple");
//		
//		int result = dao.loginCheck(id, pwd);
//		
////		System.out.println(result);
//		User user = null;
//		
//		if(result == 1) {
//			user = dao.getUser(id);
//		}else {
//			System.out.println("로그인 실패");
//		}
//		
////		System.out.println(user);
//		return user;
//	}

	@Override
	public void updateUser(User user, HttpServletRequest request) {
		String fileName = user.getFile().getOriginalFilename();
		//선택한 파일이 없을 경우 default 값 넣어주기
		if(fileName == null || fileName == "") {
			fileName = "user.png"; //default image name
		}
		String path = request.getServletContext().getRealPath("/userImage");
		String fpath = path + "\\" + fileName;
		System.out.println(fpath);
		
		FileOutputStream fs = null;
		
		try {
			
			fs = new FileOutputStream(fpath);
			fs.write(user.getFile().getBytes());
			fs.close();
			
		} catch (Exception e) {
			System.out.println("글쓰기 파일올리기 실패 : " + e.getMessage());
		}

		//DB 파일명 저장
		user.setImage(fileName);		
		
		dao.updateUser(user, request);
		
	}

	@Override
	public void updateUserPwd(String id, String pwd) {
		dao.updateUserPwd(id, pwd);	
	}

	@Override
	public User getUser(String id) {
		return dao.getUser(id);
	}

	@Override
	public List<User> getUserList() {
		return dao.getUserList();
	}

	@Override
	public void deleteUser(String id) {
		System.out.println("dao");
		dao.deleteUser(id);
		
	}


	
}
