package kr.or.bit3004.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
//import com.oreilly.servlet.MultipartRequest;
//import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import com.google.errorprone.annotations.RestrictedApi;

import kr.or.bit3004.aside.AsideService;

@Controller
public class CloudController {
	
	@Autowired
	private AsideService asideService;
	
	@Autowired
	private CloudService cloudService;
	
	@RequestMapping("cloud.do")
    public String cloud(HttpServletRequest request,int teamNo,Model model){
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		model.addAttribute("teamNo",teamNo);
		System.out.println(teamNo);
		//ArrayList<CloudUpload> list = new ArrayList<CloudUpload>();
		//ListObject.listObjects("final-project-281709", "king240",list);
		//model.addAttribute("cloudList",list);
		return "cloud/cloud";
	}
	 
	 @RequestMapping("CloudList.ajax")
	 public @ResponseBody List<CloudUpload> getFileList(int teamNo){
		 System.out.println("cloudListajax탐!");
		 return cloudService.getFileList(teamNo);
	 }
	 @RequestMapping(value="UploadFile.ajax",method=RequestMethod.POST)
	// public List<CloudFile> uploadFile(CloudFile cloudFile){
	 public @ResponseBody List<CloudUpload> uploadFile(MultipartHttpServletRequest request){
		 System.out.println("uploadfileajax탐");
		 return cloudService.uploadFile(request);
	 }
//	 @RequestMapping("DeleteFile.ajax")
//	 public @ResponseBody List<CloudUpload> deleteFile(int teamNo,String delFile){
//		 System.out.println("deltefileajax탐");
//		 return cloudService.deleteFile(teamNo,delFile);
//	 }
	 @RequestMapping("DeleteFile.ajax")
	 public @ResponseBody void deleteFile(int teamNo,String delFile){
		 System.out.println("deltefileajax탐");
		 cloudService.deleteFile(teamNo,delFile);
	 } 
	
	
//	 @RequestMapping(value="cloudComplete.do", method = RequestMethod.POST)
//	 public String cloudComplete(MultipartHttpServletRequest request,int teamNo,Model model) throws IOException { 
//		
//		model.addAttribute("team", asideService.getTeam(teamNo));
//		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
//		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
//		 
//		 List<MultipartFile> fileList = request.getFiles("file");
//		 if(fileList !=null && fileList.size()>0) { //빈 파일이 아니라면,
//			 for(MultipartFile multiFile : fileList) {
//				 String originFileName = multiFile.getOriginalFilename();
//
//				 //파일명 중복방지를 위해 랜덤 문자열 생성해서, 파일명을 지정해준다.
//			//	 UUID uuid = UUID.randomUUID();
//				// String fileName = uuid.toString()+originFileName;
//				 String fileName =originFileName;
//				 String path =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\cloud\\"+teamNo;
//				 File folder = new File(path);
//				 if(!folder.exists()) {
//					 try {
//						folder.mkdir(); //mkdir은 최종디렉터리가 없을 경우, 생성해준다.
//					} catch (Exception e) {
//						System.out.println("폴더생성실패");
//						e.getMessage();
//					}
//					 System.out.println("팀폴더 생성완료");
//				 }
//				 String filePath = path+"\\"+fileName; //최종 파일 경로
//				 CloudUpload cloudUpload = new CloudUpload();
//				 
//				 if((!fileName.equals(""))&&(multiFile.getSize()>0)) { //빈파일이 아니라면,
//					 FileOutputStream fs = null;
//					 try {
//						fs = new FileOutputStream(filePath);
//						fs.write(multiFile.getBytes());
//					} catch (Exception e) {
//						System.out.println("file write error");
//						e.getMessage();
//					}finally {
//						try {
//							fs.close();
//						} catch (IOException e2) {
//							System.out.println("fs close error");
//							e2.getMessage();
//						}
//					}
//				 } else {
//					 System.out.println("빈파일임");
//					 continue;
//				 }
//				 cloudUpload.setFileName(fileName);
//				 cloudUpload.setFilePath(filePath);
//				 cloudUpload.setFileSize(multiFile.getSize());
//				 cloudUpload.setOriginFileName(originFileName);
//			 
//		//	UploadObject.uploadObject("final-project-281709", "king240","black.png","C:\\Users\\bitcamp\\git\\bit155_final_project\\src\\main\\resources\\static\\img\\black.png");
//			UploadObject.uploadObject("final-project-281709", "king240",fileName,filePath); 
//			 }
//			
//		 }
//		System.out.println("일단 올렸다.");
//		return "cloud/cloudComplete";
//	  }
	 
	 
	
	 
	 
	 
}
