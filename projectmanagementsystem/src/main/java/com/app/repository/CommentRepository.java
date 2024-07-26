package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

//	List<Comment> findByIssues(Long issueId);
	
	
}
