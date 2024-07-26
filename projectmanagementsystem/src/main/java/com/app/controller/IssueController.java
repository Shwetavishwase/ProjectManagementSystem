package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.IssueDTO;
import com.app.model.Issues;
import com.app.model.User;
import com.app.request.IssueRequest;
import com.app.response.AuthResponse;
import com.app.response.MessageResponse;
import com.app.service.IssueService;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/issue")
public class IssueController {

	@Autowired
	private IssueService issueService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/project/{projectId}")
	public ResponseEntity<List<Issues>> getIssueByProjectId(@PathVariable Long projectId)throws Exception
	{
		return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
	}
	
	@GetMapping("/{issueId}")
	public ResponseEntity<Issues> getIssueById(@PathVariable Long issueId)throws Exception
	{
		return ResponseEntity.ok(issueService.getIssueById(issueId));
	}
	
	
	@PostMapping
	public ResponseEntity<IssueDTO> createIssue(
			@RequestBody IssueRequest issueRequest,
			@RequestHeader("Authorization") String token)throws Exception
	{
		User tokenUser=userService.findUserProfileByJwt(token);
		User user=userService.findUserById(tokenUser.getId());
		
			Issues createdIssue=issueService.createIssue(issueRequest, tokenUser);
			IssueDTO issueDTO=new IssueDTO();
			
			issueDTO.setDescription(createdIssue.getDescription());
			issueDTO.setDueDate(createdIssue.getDueDate());
			issueDTO.setId(createdIssue.getId());
			issueDTO.setPriority(createdIssue.getPriority());
			issueDTO.setProject(createdIssue.getProjects());
			issueDTO.setProject_Id(createdIssue.getProjectsId());
			issueDTO.setStatus(createdIssue.getStatus());
			issueDTO.setTitle(createdIssue.getTitle());
			issueDTO.setTags(createdIssue.getTags());
			issueDTO.setAssigned(createdIssue.getAssigned());
			
			return ResponseEntity.ok(issueDTO);
		}
	
	@DeleteMapping("/{issueId}")
	public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
			@RequestHeader("Authorization")String token)throws Exception
	{
		User user=userService.findUserProfileByJwt(token);
		issueService.deleteIssue(issueId, user.getId());
		
		MessageResponse res=new MessageResponse();
		res.setMessage("Issue deleted");
		
		return ResponseEntity.ok(res);
		
	}
	
	
	@PutMapping("/{issueId}/assigned/{userId}")
	public ResponseEntity<Issues> addUserToIssue(@PathVariable Long issueId,
									@PathVariable Long userId)throws Exception
	{
		Issues issue=issueService.addUserToIssue(issueId, userId);
		
		return ResponseEntity.ok(issue);
				
	}
	
	
	@PutMapping("/{issueId}/status/{status}")
	public ResponseEntity<Issues> updateIssueStatus(
			@PathVariable Long issueId,
			@PathVariable String status
			) throws Exception
	{
		Issues issue=issueService.updateStatus(issueId, status);
		
		return ResponseEntity.ok(issue);
	}
	
	
	
	
	
	
	
	
	
	
	
}
