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
		ArrayList<CloudUpload> list = new ArrayList<CloudUpload>();
		ListObject.listObjects("final-project-281709", "king240",list);
		return list;
	}
	

	@Override
	public List<CloudUpload> uploadFile(MultipartHttpServletRequest request){
		 List<MultipartFile> fileList = request.getFiles("file");
		 int teamNo = Integer.parseInt(request.getParameter("teamNo"));
		 List<CloudUpload> returnFileList = new ArrayList<CloudUpload>(); //ajax return용 업로드파일목록
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
			try {
				UploadObject.uploadObject("final-project-281709", "king240",fileName,filePath); 
			} catch (Exception e) {
				e.getMessage();
			}
			
			returnFileList.add(cloudUpload);
			
		 }
		
		 }
		return returnFileList;
	}
	
	@Override
	public void deleteFile(int teamNo,String delFile) {
		String fileName=delFile;
		DeleteObject.deleteObject("final-project-281709",  "king240", fileName);
	}
}
