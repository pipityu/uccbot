package com.ucc.chatbot.controller;


import com.ucc.chatbot.model.Request;
import com.ucc.chatbot.model.User;
import com.ucc.chatbot.service.MyUserDetailsService;
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
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RequestService reqservice;

    @GetMapping(path = "/request")
    public String request(Model model, Principal principal) {

        User user = myUserDetailsService.loadUser(principal.getName());
        String name = user.getName();
        int admin = name.compareTo("Admin");

        String theUrl = "https://api.manychat.com/fb/subscriber/findByName?name=Péter Nagy";

        //Header beállítása az azonosításhoz
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer 105197630914532:ba342569ac0c5408909eee97f971b9a6");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);

        //Adatok kinyerése JSON-ból (data, custom_fields)
        JSONObject fulljson = new JSONObject(response.getBody()); //teljes json
        JSONObject jsonArrData = fulljson.getJSONArray("data").getJSONObject(0); //data obj (amiben van ar array)
        JSONArray jsonArr = jsonArrData.getJSONArray("custom_fields"); //ez lenne az array[]

        String firstName = jsonArrData.getString("first_name");
        String lastName = jsonArrData.getString("last_name");
        String id = jsonArrData.getString("id");
        String choiceValue = jsonArr.getJSONObject(3).getString("value");
        String startDateValue = jsonArr.getJSONObject(2).getString("value");
        String endDateValue = jsonArr.getJSONObject(0).getString("value");
        String statusValue = jsonArr.getJSONObject(1).getString("value");

        if(admin==0){
           /* model.addAttribute("name", "null");
            model.addAttribute("id", "null");
            model.addAttribute("choicevalue", "null");
            model.addAttribute("startDateValue", "null");
            model.addAttribute("endDateValue", "null");
            model.addAttribute("statusValue", "null");
            model.addAttribute("email", "null");*/
            List<Request> reqArr = reqservice.listAllRequest();
            model.addAttribute("allRequest", reqArr);

        }else{
            //1DB request a felhasználónév alapján
            Request request = reqservice.findRequestByUserName(principal.getName());
            model.addAttribute("allRequest", request);
            /*model.addAttribute("name", firstName+" "+lastName);
            model.addAttribute("id", id);
            model.addAttribute("choicevalue", choiceValue);
            model.addAttribute("startDateValue", startDateValue);
            model.addAttribute("endDateValue", endDateValue);
            model.addAttribute("statusValue", statusValue);
            model.addAttribute("email", principal.getName());
            model.addAttribute("allRequest", "null");*/
        }


        model.addAttribute("name", name);

        //Küldés Gombról érkezik
/*            Request request = new Request(firstName + " " + lastName, name,
                    choiceValue, startDateValue, endDateValue, statusValue);*/

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
