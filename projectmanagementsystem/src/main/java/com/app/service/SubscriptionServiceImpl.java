package com.app.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.PlanType;
import com.app.model.Subscription;
import com.app.model.User;
import com.app.repository.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public Subscription createSubscription(User user) {
		
		Subscription subscription=new Subscription();
		
		subscription.setUser(user);
		subscription.setSubscriptionStartDate(LocalDate.now());
		subscription.setGetSubscriptionEndDate(LocalDate.now());
		subscription.isValid();
		subscription.setPlanType(PlanType.FREE);
		
		
		return subscriptionRepository.save(subscription);
	}

	@Override
	public Subscription getUserSubscription(Long userId) throws Exception {
		
		Subscription subscription= subscriptionRepository.findByUserId(userId);
		
		if(!isValid(subscription))
		{
			subscription.setPlanType(PlanType.FREE);
			subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
			subscription.setSubscriptionStartDate(LocalDate.now());
		}
		
		return subscriptionRepository.save(subscription);
	}

	@Override
	public Subscription upgradSubscription(Long userId, PlanType planType) {
		
		Subscription subscription=subscriptionRepository.findByUserId(userId);
		
		subscription.setPlanType(planType);
		subscription.setSubscriptionStartDate(LocalDate.now());
		
		if(planType.equals(PlanType.ANNUALLY))
		{
			subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
		}
		else {
			subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));
		}
		
		return subscriptionRepository.save(subscription);
	}

	@Override
	public boolean isValid(Subscription subscription) {
		if(subscription.getPlanType().equals(PlanType.FREE))
		{
			return true;
		}
		
		LocalDate endDate=subscription.getGetSubscriptionEndDate();
		LocalDate currentDate=LocalDate.now();
		
		
		return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
	}

}
