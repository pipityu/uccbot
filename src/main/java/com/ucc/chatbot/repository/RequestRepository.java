package com.ucc.chatbot.repository;

import com.ucc.chatbot.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query(value = "select * from requests where username =?", nativeQuery = true)
        public Request findByUserName(String username);

    @Query(value = "update requests set type=?1, start_date=?2, end_date=?3, status=?4 where username=?5", nativeQuery = true)
        public void updateRequest(String type, String start_date, String end_date, String status, String username);

}
