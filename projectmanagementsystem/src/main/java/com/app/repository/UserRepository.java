package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
