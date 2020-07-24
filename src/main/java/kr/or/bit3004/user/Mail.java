package kr.or.bit3004.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
//	
//	private String sender;
//	private String receiver;
//	private String subject;
//	private String message;

    private String from;
    private String to;
    private String subject;
    private String content;
	
	
	
	
}
