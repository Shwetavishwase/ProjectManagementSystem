package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
