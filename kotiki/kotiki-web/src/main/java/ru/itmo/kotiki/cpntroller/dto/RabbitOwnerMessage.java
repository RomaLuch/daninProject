package ru.itmo.kotiki.cpntroller.dto;

public record RabbitOwnerMessage(
        OperationType operationType,
        Integer entityId,
        String name,
        AuthUser user) {
}
