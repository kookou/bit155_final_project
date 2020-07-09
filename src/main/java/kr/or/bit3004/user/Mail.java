package kr.or.bit3004.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Mail {
	
	private String sender;
	private String receiver;
	private String subject;
	private String message;

}
