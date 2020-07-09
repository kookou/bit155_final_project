package kr.or.bit3004.user;

import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.UserDao;

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
	public int idCheck(String id) {
		return dao.idCheck(id);
		
	}


	@Override
	public void updateUser(User user) {
		
		String fileName = user.getFile().getOriginalFilename();
		System.out.println("fileName : "+">"+ fileName+"<");
		System.out.println(user.getFile().getSize());
		
		
		System.out.println(fileName == null);
		
		//사진 설정을 한 경우 
		if(user.getFile().getSize() > 0) {

			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\assets\\images\\userImage";
			
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
			
			user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
			
			System.out.println("serviceImpl");
			System.out.println(user);
			
			dao.updateUser(user);
			
		}else { // 사진 설정을 안한경우
			
			user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
			
			System.out.println("serviceImpl");
			System.out.println(user);
			
			dao.updateUserExceptImage(user);
			
		}


		
	}

	@Override
	public void updateUserPwd(String id, String pwd) {
		
		pwd = bCryptPasswordEncoder.encode(pwd);
		
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
