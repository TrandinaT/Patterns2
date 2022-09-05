package ru.netology.delivery.data;

import lombok.*;

@Value
public class Info {
    private String login;
    private String password;
    private String status;
}
