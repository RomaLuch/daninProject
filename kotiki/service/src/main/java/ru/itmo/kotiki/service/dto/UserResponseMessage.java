package ru.itmo.kotiki.service.dto;

import ru.itmo.kotiki.dao.entity.Roles;

public record UserResponseMessage(String name, String password, boolean enabled, Roles role, Integer ownerId) {

}
