package ru.itmo.kotiki.service.dto;

public record RabbitQuery(
        OperationType operationType,
        Integer ownerId,
        Integer entityId,
        String name,
        String color
) {
}
