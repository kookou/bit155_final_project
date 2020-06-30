package kr.or.bit3004.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.TestDao;
import kr.or.bit3004.dto.TestTable;
import kr.or.bit3004.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDao dao;
	
	@Override
	public TestTable selectTable() {
		return dao.getTable();
	}
	
}
