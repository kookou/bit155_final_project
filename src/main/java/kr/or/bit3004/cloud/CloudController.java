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
		return "cloud/cloud";
	}
	 
	/*
	* @Method Name : getFileList
	* @작성일 : 2020.07.28
	* @작성자 : 이서영
	* @Method 설명 : Cloud List 가져오기 (ajax)
	* @param teamNo
	* @return Cloud List
	**/
	 @RequestMapping("CloudList.ajax")
	 public @ResponseBody List<CloudUpload> getFileList(int teamNo){
		 return cloudService.getFileList(teamNo);
	 }
	 @RequestMapping(value="UploadFile.ajax",method=RequestMethod.POST)
	 public @ResponseBody List<CloudUpload> uploadFile(MultipartHttpServletRequest request){
		 return cloudService.uploadFile(request);
	 }
	 @RequestMapping("DeleteFile.ajax")
	 public @ResponseBody void deleteFile(int teamNo,String delFile){
		 cloudService.deleteFile(teamNo,delFile);
	 } 
	 
}
