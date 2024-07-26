package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.model.Issues;
import com.app.model.User;
import com.app.request.IssueRequest;

public interface IssueService {

	Issues getIssueById(Long issueId)throws Exception;
	
	List<Issues> getIssueByProjectId(Long projectId)throws Exception;
	
	Issues createIssue(IssueRequest issueRequest,User user)throws Exception;
	
	void deleteIssue(Long issueId,Long userId)throws Exception;
	
	Issues addUserToIssue(Long issueId,Long userId)throws Exception;
	
	Issues updateStatus(Long issueId,String status) throws Exception;
	
}
