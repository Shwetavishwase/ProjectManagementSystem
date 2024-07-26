package com.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

	private Long id;
	
	private String title;
	
	private String description;
	
	private String status;
	
	private Long project_Id;
	
	private String priority;
	
	private LocalDate dueDate;
	
	private List<String> tags=new ArrayList<>();

	private Project project;
	
	private User assigned;

}
