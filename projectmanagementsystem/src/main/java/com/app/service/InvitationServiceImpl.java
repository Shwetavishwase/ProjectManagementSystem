package com.app.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Invitation;
import com.app.repository.InvitationRepository;

@Service
public class InvitationServiceImpl implements InvitationService {

	@Autowired
	private InvitationRepository invitationRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void sendInvitation(String email, Long projectId) throws Exception {

		String invitationToken=UUID.randomUUID().toString();
		
		Invitation invitation=new Invitation();
		
		invitation.setEmail(email);
		invitation.setProjectsId(projectId);
		invitation.setToken(invitationToken);
		
		invitationRepository.save(invitation);
		
		String invitationLink="http://localhost:5173/acceept_invitation?token"+invitationToken;
		emailService.sendEmailWithToken(email, invitationLink);

	}

	@Override
	public Invitation acceptInvitation(String token, Long userId) throws Exception {
		
		Invitation invitation=invitationRepository.findByToken(token);
		
		if(invitation==null)
		{
			throw new Exception("Invalid invitation");
		}
		
		return null;
	}

	@Override
	public String getTokenByUserMail(String userEmail) {

		Invitation invitation=invitationRepository.findByEmail(userEmail);
		
		return invitation.getToken();
	}

	@Override
	public void deleteToken(String token) {
		
		Invitation invitation=invitationRepository.findByToken(token);
		
		invitationRepository.delete(invitation);

	}

}
