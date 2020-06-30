package kr.or.bit3004.controller;

import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Storage;

@RestController
public class FileReaderController {

	@Autowired
	private Storage storage;
	
	@RequestMapping(path = {"/helloh"}, method= {RequestMethod.GET})
	public Message readFromFile() throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		try(ReadChannel channel = storage.reader("king240", "TestText.txt")){
			ByteBuffer bytes = ByteBuffer.allocate(64*1024);
			while(channel.read(bytes)>0) {
				bytes.flip();
				String data = new String(bytes.array(),0,bytes.limit());
				sb.append(data);
				bytes.clear();
			}
		}
		
		Message message = new Message();
		message.setContents (sb.toString());
		
		return message;
		
	}
	
}

class Message {
	private String contents;

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
