package com.app.service;

import java.util.List;

import com.app.model.Message;

public interface MessageService {

	Message sendMessage(Long senderId,Long chatId,String content)throws Exception;
	
	List<Message> getMessageByProjectId(Long projectId)throws Exception;
	
}
