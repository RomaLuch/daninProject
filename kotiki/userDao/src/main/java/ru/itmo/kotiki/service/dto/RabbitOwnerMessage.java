package ru.itmo.kotiki.service.dto;

public record RabbitOwnerMessage(
        OperationType operationType,
        Integer entityId,
        String name,
        AuthUser user) {
}
