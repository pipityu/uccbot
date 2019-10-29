package com.ucc.chatbot.service;

import com.ucc.chatbot.model.Request;
import com.ucc.chatbot.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class RequestService {

    @Autowired
    RequestRepository reqrepo;

    public List<Request> listAllRequest(){
       return reqrepo.findAll();
    }

    public void saveRequest(Request request){
        reqrepo.save(request);
    }

}
