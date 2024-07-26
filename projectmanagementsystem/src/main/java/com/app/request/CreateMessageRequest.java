package com.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMessageRequest {

	private Long senderId;
	
	private String content;
	
	private Long projectId;
	
	
}
