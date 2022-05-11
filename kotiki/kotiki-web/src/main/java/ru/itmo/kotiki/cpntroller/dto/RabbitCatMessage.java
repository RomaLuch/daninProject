package ru.itmo.kotiki.cpntroller.dto;

public record RabbitCatMessage(
        OperationType operationType,
        Integer ownerId,
        Integer entityId,
        String name,
        String color
) {
}
