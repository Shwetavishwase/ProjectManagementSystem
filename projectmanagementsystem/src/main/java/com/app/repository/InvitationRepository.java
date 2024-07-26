package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Invitation;


public interface InvitationRepository extends JpaRepository<Invitation, Long> {

	Invitation findByToken(String token);
	
	Invitation findByEmail(String userEmail);
}
