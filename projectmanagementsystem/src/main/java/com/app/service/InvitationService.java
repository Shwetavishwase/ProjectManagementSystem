package com.app.service;

import com.app.model.Invitation;

import jakarta.mail.MessagingException;

public interface InvitationService {

	public void sendInvitation(String email,Long projectId) throws  Exception;
	
	public Invitation acceptInvitation(String token,Long userId) throws Exception;
	
	public String getTokenByUserMail(String userEmail);
	
	public void deleteToken(String token);
}
