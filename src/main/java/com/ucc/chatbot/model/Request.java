package com.ucc.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String type;
    private String start_date;
    private String end_date;
    private String status;

    @OneToOne(mappedBy = "request")
    private User user;

}
