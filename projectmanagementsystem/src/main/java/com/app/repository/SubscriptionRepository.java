package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	Subscription findByUserId(Long userId);
	

}
