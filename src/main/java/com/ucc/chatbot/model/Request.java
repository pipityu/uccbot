package com.ucc.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String name;
    private String type;
    private String start_date;
    private String end_date;
    private String status;
    private String manychat_id;

    public Request(){}

    public Request(String username, String name, String type, String start_date, String end_date, String status, String manychat_id) {
        this.username = username;
        this.name = name;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.manychat_id = manychat_id;
    }

    @OneToOne(mappedBy = "request")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManychat_id() {
        return manychat_id;
    }

    public void setManyChatId(String manyChatId) {
        this.manychat_id = manyChatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
