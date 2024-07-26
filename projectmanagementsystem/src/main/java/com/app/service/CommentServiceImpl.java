package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Comment;
import com.app.model.Issues;
import com.app.model.User;
import com.app.repository.CommentRepository;
import com.app.repository.IssueRepository;
import com.app.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Comment createComment(Long issueId, Long userId, String string) throws Exception {

		Optional<Issues> issueOptional = issueRepository.findById(issueId);
		Optional<User> userOptional = userRepository.findById(userId);

		Issues issue = issueRepository.findById(issueId).get();
		User user = userRepository.findById(userId).get();

		if (issueOptional.isEmpty()) {
			throw new Exception("issue not find with id" + issueId);
		}
		if (userOptional.isEmpty()) {
			throw new Exception("user not found with id" + userId);
		}

		Comment comment = new Comment();

		comment.setIssues(issue);
		comment.setUser(user);
		comment.setCreateDateTime(LocalDateTime.now());
		comment.setContent(comment.getContent());

		Comment savedComment = commentRepository.save(comment);

		issue.getComments().add(savedComment);

		return savedComment;
	}

	@Override
	public void deleteComment(Long commentId, Long userId) throws Exception {

		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		Optional<User> userOptional = userRepository.findById(userId);

		if (commentOptional.isEmpty()) {
			throw new Exception("comment not found with thid id" + commentId);
		}
		if (userOptional.isEmpty()) {
			throw new Exception("user not found with thid id" + userId);
		}

		Comment comment = commentOptional.get();
		User user = userOptional.get();

		if (comment.getUser().equals(user)) {
			commentRepository.delete(comment);
		} else {
			throw new Exception("User does not have permission to deletethis comment");
		}

	}

	@Override
	public List<Comment> findCommentByIssueId(Long issueId) {
		Issues issues = issueRepository.findById(issueId).get();
		List<Comment> comments = issues.getComments();
		return comments;

	}

}
