package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Chat;
import com.app.model.Invitation;
import com.app.model.Project;
import com.app.model.User;
import com.app.request.InviteRequest;
import com.app.response.MessageResponse;
import com.app.service.InvitationService;
import com.app.service.ProjectService;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvitationService invitationService;
	
	@GetMapping
	public ResponseEntity<List<Project>> getProjects(
			@RequestParam(required = false)String category,
			@RequestParam(required = false)String tag,
			@RequestHeader("Authorization")String jwt ) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		List<Project>projects=projectService.getProjectByTeam(user, category, tag);
		
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjects(
			@PathVariable Long projectId,
			@RequestHeader("Authorization")String jwt ) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Project project=projectService.getProjectById(projectId);
		
		return new ResponseEntity<>(project,HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Project> createProject(
			Long projectId,
			@RequestHeader("Authorization")String jwt,
			@RequestBody Project project) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Project createdProject=projectService.createProject(project, user);
		
		return new ResponseEntity<>(createdProject,HttpStatus.OK);
	}
	
	@PatchMapping("/{projectId}")
	public ResponseEntity<Project> updateProject(
			@PathVariable Long projectId,
			@RequestHeader("Authorization")String jwt,
			@RequestBody Project project) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Project updatedProject=projectService.updateProject(project, projectId);
		
		return new ResponseEntity<>(updatedProject,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<MessageResponse> deleteProject(
			@PathVariable Long projectId,
			@RequestHeader("Authorization")String jwt) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		projectService.deleteProject(projectId, user.getId());
		
		MessageResponse res=new MessageResponse("project deleted successfully");
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Project>> searchProject(
			@RequestParam(required = false)String keyword,
			@RequestHeader("Authorization")String jwt ) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		List<Project>projects=projectService.searchProjects(keyword, user);
		
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}
	
	@GetMapping("/{projectId}/chat")
	public ResponseEntity<Chat> getChatByProjectId(
			@PathVariable Long projectId,
			@RequestHeader("Authorization")String jwt ) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Chat chat=projectService.getChatByProjectId(projectId);
		
		return new ResponseEntity<>(chat,HttpStatus.OK);
	}
	
	@PostMapping("/invite")
	public ResponseEntity<MessageResponse> inviteProject(
			@RequestBody InviteRequest req,
			@RequestHeader("Authorization")String jwt,
			@RequestBody Project project) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		invitationService.sendInvitation(req.getEmail(), req.getProjectId());
		
		MessageResponse res=new MessageResponse("User invition sent");
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/accept_invitation")
	public ResponseEntity<Invitation> acceptInviteProject(
			@RequestParam String token,
			@RequestHeader("Authorization")String jwt,
			@RequestBody Project project) throws Exception
	{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Invitation invitation= invitationService.acceptInvitation(token,user.getId());
		
		projectService.addUserToProject(invitation.getProjectsId(), invitation.getId());
		
		MessageResponse res=new MessageResponse("User invition sent");
		
		return new ResponseEntity<>(invitation,HttpStatus.ACCEPTED);
	}
}
