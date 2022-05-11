package ru.itmo.kotiki.cpntroller.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AuthUser {
    private String name;
    private String password;
    private boolean enabled;
    private String role;
    private String fullName;
    private Date birthday;
}
