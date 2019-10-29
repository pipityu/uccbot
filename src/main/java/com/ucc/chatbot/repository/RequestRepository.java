package com.ucc.chatbot.repository;

import com.ucc.chatbot.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {

}
