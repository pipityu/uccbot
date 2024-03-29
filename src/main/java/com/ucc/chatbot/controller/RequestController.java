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
import java.util.Optional;

@Controller
public class RequestController {
    private String TP = "Táppénz";
    private String ADMIN_NAME = "Péter Nagy";
    private String API_TOKEN = "Bearer 105197630914532:ba342569ac0c5408909eee97f971b9a6";
    private String manyChatID = "null";

    private String type = "null";
    private String startDate = "null";
    private String endDate = "null";
    private String status = "null";
    private String msg = "Elfogadva";

    HttpHeaders headers = new HttpHeaders();
    {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", API_TOKEN);
    }

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RequestService reqservice;

    @GetMapping(path = "/request/send")
    public String send(Model model, Principal principal) {
        User user = myUserDetailsService.loadUser(principal.getName());
        String name = user.getName();
        boolean admin = false;
        if (name.compareTo("Admin") == 0){
            name = ADMIN_NAME;
            admin = true;
        }

        String theUrl = "https://api.manychat.com/fb/subscriber/findByName?name="+name;
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);

        JSONObject fulljson = new JSONObject(response.getBody()); //teljes json
        JSONObject jsonArrData = fulljson.getJSONArray("data").getJSONObject(0); //data obj (amiben van ar array)
        manyChatID = jsonArrData.getString("id");
        JSONArray jsonArr = jsonArrData.getJSONArray("custom_fields"); //ez lenne az array[]

        String val,addVal ="";
        for(int i = 0; i<4; i++){
            val = jsonArr.getJSONObject(i).getString("name");
            addVal = jsonArr.getJSONObject(i).getString("value");
            switch(val){
                case "choice" : type = addVal; break;
                case "startDate" : startDate = addVal; break;
                case "endDate" : endDate = addVal; break;
                case "status" : status = addVal; break;
            }
        }

        if(admin){
            model.addAttribute("saved", "Admin vagy, neked nem kell kérelmet küldeni");
        } else {
            if(status.equals("null")){
                model.addAttribute("saved", "Hiba!");
                return "forward:/request/check";
            }

            Request request = new Request(principal.getName(), name, type, startDate, endDate, status, manyChatID);

            if(type.compareTo(TP) == 0){
                request.setStatus("Elfogadva");
            }

            if (reqservice.findRequestByUserName(principal.getName()) == null) {
                reqservice.saveRequest(request);
                model.addAttribute("saved", "Mentve");
            }
            else{
                reqservice.updateRequest(request);
                model.addAttribute("saved", "Felülírva");
            }
        }
        return "forward:/request/check";
    }

    @RequestMapping(value = "/request/check", method = {RequestMethod.GET, RequestMethod.POST})
    public String check(Model model, Principal principal) {
        User user = myUserDetailsService.loadUser(principal.getName());
        String name = user.getName();
        boolean admin = false;

        if (name.compareTo("Admin") == 0){
            admin = true;
        }

        if(admin){
            List<Request> reqArr = reqservice.listAllRequest();
            model.addAttribute("allRequest", reqArr);

        }else{
            Request request = reqservice.findRequestByUserName(principal.getName());
            model.addAttribute("allRequest", request);
        }
        return "userhome";
    }


    @PostMapping("/request/response")
    public String response(@RequestParam String action, @RequestParam String requestId) {
        int id = Integer.parseInt(requestId);
        Optional<Request> request = reqservice.findRequestById(id);
        Request r = request.get();
        msg = "Benyújtott kérelmed "+action+"ra került";

        if(action.compareTo("Elutasítás") == 0){
            reqservice.deleteRequest(id);
        }
        else{
            r.setStatus("Elfogadva");
            reqservice.updateRequest(r);
        }

        String jsonSendMessage = "{   \"subscriber_id\":"+r.getManychat_id()+",\"data\":{\"version\":\"v2\",\"content\":{\"messages\":[{\"type\":\"text\",\"text\":\""+msg+"\"}]}}}";
        String theUrl = "https://api.manychat.com/fb/sending/sendContent";
        HttpEntity<String> entity = new HttpEntity<>(jsonSendMessage, headers);
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.POST, entity, String.class);
        return "forward:/request/check";
    }
}
