package com.ucc.chatbot.service;

import com.ucc.chatbot.model.Request;
import com.ucc.chatbot.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequestService {

    @Autowired
    RequestRepository reqrepo;

    public List<Request> listAllRequest(){
       return reqrepo.findAll();
    }

    public Request findRequestByUserName(String username){return reqrepo.findByUserName(username);}

    public Optional<Request> findRequestById(int id){return reqrepo.findById(id);}

    public void saveRequest(Request request){
        reqrepo.save(request);
    }

    public void updateRequest(Request request){
        reqrepo.updateRequest(request.getType(), request.getStart_date(), request.getEnd_date(), request.getStatus(), request.getUsername());
    }
    public void deleteRequest(int id){reqrepo.deleteById(id);}

}
