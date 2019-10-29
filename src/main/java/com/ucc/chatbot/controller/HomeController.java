package com.ucc.chatbot.controller;


import com.ucc.chatbot.model.Request;
import com.ucc.chatbot.service.RequestService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RequestService reqservice;

    @GetMapping(path = "/request")
    public String request(Model model, int accept, Principal principal) {
        String theUrl = "https://api.manychat.com/fb/subscriber/getInfo?subscriber_id=3809668825726118";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer 105197630914532:ba342569ac0c5408909eee97f971b9a6");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);

        JSONObject fulljson = new JSONObject(response.getBody()); //teljes json
        JSONObject jsonObj = fulljson.getJSONObject("data"); //data obj (amiben van ar array)
        JSONArray jsonArr = jsonObj.getJSONArray("custom_fields"); //ez lenne az array[]





        String firstName = jsonObj.getString("first_name");
        String lastName = jsonObj.getString("last_name");
        String id = jsonObj.getString("id");

        String choiceValue = jsonArr.getJSONObject(2).getString("value");
        String startDateValue = jsonArr.getJSONObject(3).getString("value");
        String endDateValue = jsonArr.getJSONObject(1).getString("value");
        String statusValue = jsonArr.getJSONObject(0).getString("value");

        Request request = new Request(firstName + " " + lastName, choiceValue, startDateValue, endDateValue, statusValue);

        model.addAttribute("name", firstName+" "+lastName);
        model.addAttribute("id", id);

        model.addAttribute("choicevalue", choiceValue);
        model.addAttribute("startDateValue", startDateValue);
        model.addAttribute("endDateValue", endDateValue);
        model.addAttribute("statusValue", statusValue);

        if(accept == 1){
            List<Request> reqArr = reqservice.listAllRequest();
            if(reqArr.size() > 0){
                //PARAMÉTERKÉNT A FELHASZNÁLÓNEVET és az alapján lekéregetni
                //ha admin akko mindet visszaadni, ha nem akk csak 1 et. ha nemtalál akkor a másik if
                return principal.getName();

            }else{
                //Elmenti a tervezetet
                reqservice.saveRequest(request);
                return "ment";
            }
        }

        return "userhome";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "userhome";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin/home")
    public String adminhome() {
        return "adminhome";
    }

}
