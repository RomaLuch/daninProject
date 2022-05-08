package ru.itmo.kotiki.service.dto;

public record RabbitCatQuery(
        OperationType operationType,
        Integer ownerId,
        Integer entityId,
        String name,
        String color
) {
}
