package kr.or.bit3004.service;

import kr.or.bit3004.dto.Mail;

public interface MailService {
	public int sendConfirmEmail(Mail mail) throws Exception;

}
