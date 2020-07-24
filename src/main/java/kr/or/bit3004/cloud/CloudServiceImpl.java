package kr.or.bit3004.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



@Service
public class CloudServiceImpl implements CloudService{

	@Override
	public List<CloudUpload> getFileList(int teamNo) {
		System.out.println("팀노 form cloudService"+teamNo);
		ArrayList<CloudUpload> list = new ArrayList<CloudUpload>();
		ListObject.listObjects("final-project-281709", "king240",list);
		System.out.println("잘탔다.");
		for(CloudUpload l : list) {
		//	System.out.println("list가잘형성이되었나요?"+l.getFileName());
		}
		return list;
	}
	

	@Override
	public List<CloudUpload> uploadFile(MultipartHttpServletRequest request){
		 List<MultipartFile> fileList = request.getFiles("file");
		 int teamNo = Integer.parseInt(request.getParameter("teamNo"));
		 List<CloudUpload> returnFileList = new ArrayList<CloudUpload>(); //		ajax return용 업로드파일목록
			
		 if(fileList !=null && fileList.size()>0) { //빈 파일이 아니라면,
			 for(MultipartFile multiFile : fileList) {
				 String originFileName = multiFile.getOriginalFilename();

				 //파일명 중복방지를 위해 랜덤 문자열 생성해서, 파일명을 지정해준다.
			//	 UUID uuid = UUID.randomUUID();
				// String fileName = uuid.toString()+originFileName;
				 String fileName =originFileName;
				 String path =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\cloud\\"+teamNo;
				 File folder = new File(path);
				 if(!folder.exists()) {
					 try {
						folder.mkdir(); //mkdir은 최종디렉터리가 없을 경우, 생성해준다.
					} catch (Exception e) {
						System.out.println("폴더생성실패");
						e.getMessage();
					}
					 System.out.println("팀폴더 생성완료");
				 }
				 String filePath = path+"\\"+fileName; //최종 파일 경로
				 CloudUpload cloudUpload = new CloudUpload();
				 
				 if((!fileName.equals(""))&&(multiFile.getSize()>0)) { //빈파일이 아니라면,
					 FileOutputStream fs = null;
					 try {
						fs = new FileOutputStream(filePath);
						fs.write(multiFile.getBytes());
					} catch (Exception e) {
						System.out.println("file write error");
						e.getMessage();
					}finally {
						try {
							fs.close();
						} catch (IOException e2) {
							System.out.println("fs close error");
							e2.getMessage();
						}
					}
				 } else {
					 System.out.println("빈파일임");
					 continue;
				 }
				 cloudUpload.setFileName(fileName);
				 cloudUpload.setFilePath(filePath);
				 cloudUpload.setFileSize(multiFile.getSize());
				 cloudUpload.setOriginFileName(originFileName);
			
			//GCP 업로드 함수
		//	UploadObject.uploadObject("final-project-281709", "king240","black.png","C:\\Users\\bitcamp\\git\\bit155_final_project\\src\\main\\resources\\static\\img\\black.png");
			try {
				UploadObject.uploadObject("final-project-281709", "king240",fileName,filePath); 
			} catch (Exception e) {
				e.getMessage();
			}
			
			returnFileList.add(cloudUpload);
			
		 }
		System.out.println("일단 올렸다.");
		
		 }
		return returnFileList;
	}
	
//	@Override
//	public List<CloudUpload> deleteFile(int teamNo,String delFile) {
//		System.out.println("serviec impl까지왔다.");
//		String fileName=delFile;
//		DeleteObject.deleteObject("final-project-281709",  "king240", fileName);
//		System.out.println("Delete성공했다.");
//		return ;
//	}
	@Override
	public void deleteFile(int teamNo,String delFile) {
		System.out.println("serviec impl까지왔다.");
		String fileName=delFile;
		System.out.println("삭제할파일이름불러오나!?---------"+fileName);
		DeleteObject.deleteObject("final-project-281709",  "king240", fileName);
		System.out.println("Delete성공했다.");
	}
}
