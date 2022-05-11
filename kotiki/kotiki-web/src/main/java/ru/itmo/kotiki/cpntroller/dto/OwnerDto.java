package ru.itmo.kotiki.cpntroller.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class OwnerDto {
    private int id;
    private String name;
    private Date birthday;
}
