package ru.itmo.kotiki.cpntroller;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String password;
    private boolean enabled;
    private String role;
    private Integer owner_id;
}
