package kr.or.bit3004.user;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
      user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
      dao.insertUser(user);   
   }

   @Override
   public int idCheck(String id) {
      return dao.idCheck(id);  
   }


   @Override
   public void updateUser(User user, HttpSession session) {
             
       SessionUser beforeEdit = (SessionUser)session.getAttribute("currentUser");
       
       if( (user.getFile().getSize() <= 0) && (beforeEdit.getNickname().equals(user.getNickname())) ) {
//    	   System.out.println("변경사항 없음");
    	   return;
       }
       
	   
      String fileName = user.getFile().getOriginalFilename();
//      System.out.println("fileName : "+">"+ fileName+"<");
//      System.out.println(user.getFile().getSize());
      
      //사진 설정을 한 경우 
      if(user.getFile().getSize() > 0) {

         String path = System.getProperty("user.dir") 
        		 	 + "\\src\\main\\resources\\static\\assets\\images\\userImage";
         String fpath = path + "\\" + fileName;
         
         File folder = new File(path);
         
         if(!folder.exists()) {
             try {
                folder.mkdir();
                
             } catch (Exception e) {
                System.out.println("폴더 생성 실패");
                e.getMessage();
             }
             System.out.println("사용자 이미지 폴더가 생성되었습니다.");
          }
         
         
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

         if(user.getNickname() == beforeEdit.getNickname()) { 
             //사진수정, 별명 수정 안함
        	 dao.updateUserImage(user);
        	 
         }else {
             // 사진수정, 별명수정
             dao.updateUserNicknameNImage(user);	 
         }
         
  
      }else { // 사진 설정을 안한경우
         
         //사진 수정 안함, 별명 수정
         dao.updateUserNickname(user);
      }

      SessionUser currentUser = new SessionUser(dao.getUser(user.getId()));
      currentUser.setTeamNo(beforeEdit.getTeamNo());
      currentUser.setIsTeamLeader(beforeEdit.getIsTeamLeader());
      
      session.setAttribute("currentUser", currentUser);
   }
   

   @Override
   public String updateUserPwd(String pwd, String newPwd, HttpSession session) {
      
      User currentUser = dao.getUser(((User)session.getAttribute("currentUser")).getId());
      String result = "";
      
      if(!bCryptPasswordEncoder.matches(pwd, currentUser.getPwd())) {
    	  result = "비밀번호 변경 실패";
    	  
      }else {
    	  newPwd = bCryptPasswordEncoder.encode(newPwd);
          dao.updateUserPwd(currentUser.getId(), newPwd);  
    	  
    	  result = "비밀번호 변경 시도 완료";
      }
//      System.out.println(result);
      return result;
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
   public void deleteUser(String id, HttpSession session) {
      dao.deleteUser(id);
      session.invalidate();
      
   }
   
}