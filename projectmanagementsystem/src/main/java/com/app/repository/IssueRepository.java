package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Issues;

public interface IssueRepository extends JpaRepository<Issues, Long> {
	
//	public List<Issues> findByProjects(Long id);
	
	

}
