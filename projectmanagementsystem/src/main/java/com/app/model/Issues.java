package com.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Issues {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String description;
	
	private String status;
	
	private Long projectsId;
	
	private String priority;
	
	private LocalDate dueDate;
	
	private List<String> tags=new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "assigned_id")
	private User assigned;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project projects;

	@JsonIgnore
	@OneToMany(mappedBy = "issues",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private List<Comment> comments=new ArrayList<>();


}
