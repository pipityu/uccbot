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
    private String LEAVE = "Szabadság";
    private String ADMIN_NAME = "Péter Nagy";
    private String API_TOKEN = "Bearer 105197630914532:ba342569ac0c5408909eee97f971b9a6";
    private String manyChatID = "null";

    private String firstName = "null";
    private String lastName = "null";
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

        //Adatok kinyerése JSON-ból (data, custom_fields)
        JSONObject fulljson = new JSONObject(response.getBody()); //teljes json
        JSONObject jsonArrData = fulljson.getJSONArray("data").getJSONObject(0); //data obj (amiben van ar array)
        JSONArray jsonArr = jsonArrData.getJSONArray("custom_fields"); //ez lenne az array[]


        /*for(int i = 0; i<4; i++){
            String val = jsonArr.getJSONObject(i).getString("name");
            String addVal = jsonArr.getJSONObject(i).getString("value");
            switch(val){
                case "choice" : type = addVal;
                case "startDate" : startDate = addVal;
                case "endDate" : endDate = addVal;
                case "status" : status = addVal;
            }
        }*/
        type = jsonArr.getJSONObject(0).getString("name");
        startDate = jsonArr.getJSONObject(1).getString("name");
        endDate = jsonArr.getJSONObject(3).getString("name");
        status = jsonArr.getJSONObject(2).getString("name");

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
            //1DB request a felhasználónév alapján
            Request request = reqservice.findRequestByUserName(principal.getName());
            model.addAttribute("allRequest", request);
        }

        return "userhome";
    }

    //action = formról beérkező adat(elutasítás vagy elfogadás)
    //id = szintén a formról a request id-ja
    @PostMapping("/request/response")
    public String response(@RequestParam String action, @RequestParam String requestId) {
        int id = Integer.parseInt(requestId);
        Optional<Request> request = reqservice.findRequestById(id);     //OPTIONAL egy generikus tároló 0,1 értékekkel ami azt nézi hogy létezik e az elem(hibakezelésre szolgál)
        Request r = request.get();      //get() ha létezik az elem akkor visszaadja az értékét ha nem akkor NoSuchElementException-t dob
        msg = "Kérelmed "+action+"-ra került";
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

      /*redirect will respond with a 302 and the new URL in the Location header; the browser/client will then make another request to the new URL
    forward happens entirely on a server side; the Servlet container forwards the same request to the target URL; the URL won't change in the browser*/
}
