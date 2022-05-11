    package ru.itmo.kotiki.cpntroller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Data
@Jacksonized
@Builder
public class CatDto {
    private int id;
    private String breed;
    private String name;
    private Date birthday;
    private Color color;
}
