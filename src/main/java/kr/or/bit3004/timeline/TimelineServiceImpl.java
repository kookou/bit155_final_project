package kr.or.bit3004.timeline;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.or.bit3004.dao.TimelineDao;
import net.minidev.json.JSONArray;

@Service
public class TimelineServiceImpl implements TimelineService {
	
	@Autowired
	private TimelineDao dao;
	
	@Override
	public List<Timeline> getTimeline(int teamNo) {
		return dao.getTimeline(teamNo);
	}
	
	@Override
	public int countTotalTodos(int teamNo) {
		return dao.countTotalTodos(teamNo);
	}
	
	@Override
	public int countTotalPosts(int teamNo) {			
		return dao.countTotalPosts(teamNo);
	}
	
	@Override
	public int countTotalUploadFiles(int teamNo) {
		return dao.countTotalUploadFiles(teamNo);
	}

	@Override // 구조만 짜놓고 아직 완성 안됨 (작업중)
	public String getActiveRate(int teamNo) {

		JsonArray chartData = new JsonArray();
		List<ChartData> dataList = dao.countTotalPostsByMember(teamNo);
		
		String jsonStringData = JSONArray.toJSONString(dataList);
		
		System.out.println("JSONArray 출력");
		System.out.println(jsonStringData);

		return jsonStringData;
	}

	@Override // 구조만 짜놓고 아직 완성 안됨
	public int getProgress(int teamNo) {

//		JsonArray chartData = new JsonArray();
		List<ChartData> dataList = dao.getTodoCountByState(teamNo);
		
		int allTodo = Integer.parseInt(dataList.get(0).getValue());
		double doneTodo = Double.parseDouble(dataList.get(1).getValue());
		
		int progress = (int)( doneTodo / allTodo * 100);
		System.out.println("progress impl : "+progress);
		return progress;
	}

	@Override
	public String getTodaysNewPosts(int teamNo) {

		JsonArray chartData = new JsonArray();
		List<ChartData> dataList = dao.countTodaysNewPosts(teamNo);
		
		String jsonStringData = JSONArray.toJSONString(dataList);
		
		System.out.println("JSONArray 출력");
		System.out.println(jsonStringData);

		return jsonStringData;
	}
	
//	@Override 이거 일단 필요없음
//	public void makeData(String label, String value, JsonArray chartData) {
//		JsonObject obj = new JsonObject();
//		obj.addProperty("label", label);
//		obj.addProperty("value", value);
//		chartData.add(obj);
//	}
	
	
	
	
}
