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
    public String info(Model model) {
        String theUrl = "https://api.manychat.com/fb/subscriber/getInfo?subscriber_id=3809668825726118";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer 105197630914532:ba342569ac0c5408909eee97f971b9a6");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);

        JSONObject fulljson = new JSONObject(response.getBody()); //teljes json
        JSONObject jsonObj = fulljson.getJSONObject("data"); //data obj (amiben van ar array)
        JSONArray jsonArr = jsonObj.getJSONArray("custom_fields"); //ez lenne az array[]

      /*  for(int i = 0; i < 3; i++){
            model.addAttribute(jsonArr.getJSONObject(i).getString("name"));
            jsonArr.getJSONObject(i).getString("value");
        }*/

        int choice = 0;
        int startDate = 1;
        int endDate = 2;
        String choiceName = jsonArr.getJSONObject(choice).getString("name");
        String choiceValue = jsonArr.getJSONObject(choice).getString("value");
        String startDateName = jsonArr.getJSONObject(startDate).getString("name");
        String StartDateValue = jsonArr.getJSONObject(startDate).getString("value");

        model.addAttribute("choicename", choiceName);
        model.addAttribute("choicevalue", choiceValue);

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
