package com.ucc.chatbot.controller;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@Controller
public class HomeController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(path = "/request")
    public String request(Model model, int accept) {
        String theUrl = "https://api.manychat.com/fb/subscriber/getInfo?subscriber_id=3809668825726118";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer 105197630914532:ba342569ac0c5408909eee97f971b9a6");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);

        JSONObject fulljson = new JSONObject(response.getBody()); //teljes json
        JSONObject jsonObj = fulljson.getJSONObject("data"); //data obj (amiben van ar array)
        JSONArray jsonArr = jsonObj.getJSONArray("custom_fields"); //ez lenne az array[]

        /*for(int i = 0; i < 3; i++){
            model.addAttribute("attrName"+i, jsonArr.getJSONObject(i).getString("name"));
            model.addAttribute("attrValue"+i, jsonArr.getJSONObject(i).getString("value"));
        }*/
        String firstName = jsonObj.getString("first_name");
        String lastName = jsonObj.getString("last_name");
        String id = jsonObj.getString("id");


        String choiceName = jsonArr.getJSONObject(2).getString("name");
        String choiceValue = jsonArr.getJSONObject(2).getString("value");
        String startDateName = jsonArr.getJSONObject(3).getString("name");
        String startDateValue = jsonArr.getJSONObject(3).getString("value");
        String endDateName = jsonArr.getJSONObject(1).getString("name");
        String endDateValue = jsonArr.getJSONObject(1).getString("value");
        String statusName = jsonArr.getJSONObject(0).getString("name");
        String statusValue = jsonArr.getJSONObject(0).getString("value");

        model.addAttribute("name", firstName+" "+lastName);
        model.addAttribute("id", id);

        model.addAttribute("choicename", choiceName);
        model.addAttribute("choicevalue", choiceValue);
        model.addAttribute("startDatename", startDateName);
        model.addAttribute("startDateValue", startDateValue);
        model.addAttribute("endDateName", endDateName);
        model.addAttribute("endDateValue", endDateValue);
        model.addAttribute("statusName", statusName);
        model.addAttribute("statusValue", statusValue);

        if(accept == 1){
            //feltölt DB-be (Service)
            return "ok";
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
