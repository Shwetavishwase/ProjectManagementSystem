package com.app.service;

public interface EmailService {

	void sendEmailWithToken(String userEmail,String link)throws Exception;
	
}
