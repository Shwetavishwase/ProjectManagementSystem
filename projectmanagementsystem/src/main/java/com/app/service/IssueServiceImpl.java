package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Issues;
import com.app.model.Project;
import com.app.model.User;
import com.app.repository.IssueRepository;
import com.app.repository.ProjectRepository;
import com.app.request.IssueRequest;

@Service
public class IssueServiceImpl implements IssueService {

	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Issues getIssueById(Long issueId) throws Exception {

		Optional<Issues> issue = issueRepository.findById(issueId);

		if (issue.isPresent()) {
			return issue.get();
		}

		throw new Exception("No issues found with issue id" + issueId);
	}

	@Override
	public List<Issues> getIssueByProjectId(Long projectId) throws Exception {

//		return issueRepository.findByProjectId(projectId);
		Project project=projectRepository.findById(projectId).get();
		
		List<Issues> issues=project.getIssues();
		
		return issues;
		
	}

	@Override
	public Issues createIssue(IssueRequest issueRequest, User user) throws Exception {

		Project project = projectService.getProjectById(issueRequest.getProjectId());

		Issues issue = new Issues();
		issue.setTitle(issueRequest.getTitle());
		issue.setDescription(issueRequest.getDescription());
		issue.setStatus(issueRequest.getStatus());
		issue.setProjectsId(issueRequest.getProjectId());
		issue.setPriority(issueRequest.getPriority());
		issue.setDueDate(issueRequest.getDueDate());

		issue.setProjects(project);

		return issueRepository.save(issue);
	}

	@Override
	public void deleteIssue(Long issueId, Long userId) throws Exception {
		getIssueById(issueId);

		issueRepository.deleteById(issueId);
	}

	@Override
	public Issues addUserToIssue(Long issueId, Long userId) throws Exception {
		
		User user=userService.findUserById(userId);
		
		Issues issue=getIssueById(issueId);
		
		issue.setAssigned(user);
		
		return issueRepository.save(issue);
	}

	@Override
	public Issues updateStatus(Long issueId, String status) throws Exception {
		
		Issues issue=getIssueById(issueId);
		
		issue.setStatus(status);
		
		return issueRepository.save(issue);
	}

}
