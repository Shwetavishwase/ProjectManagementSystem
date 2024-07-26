package com.app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)    //it will no send password to front end i.e client side
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assigned",cascade = CascadeType.ALL)	
	private List<Issues>assignedIssues=new ArrayList<>();
	
	private int projectsize;//we can track no of project 
}
