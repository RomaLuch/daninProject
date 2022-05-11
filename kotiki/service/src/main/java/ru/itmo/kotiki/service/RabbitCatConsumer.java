package ru.itmo.kotiki.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.CatDao;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Color;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.RabbitCatMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitCatConsumer {

    private final CatDao catDao;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "catQueue")
    public String dispatch(String message) throws JsonProcessingException {
        RabbitCatMessage rabbitCatMessage = objectMapper.readValue(message, RabbitCatMessage.class);
        switch (rabbitCatMessage.operationType()) {
            case GET_ALL -> {
                return getAll(rabbitCatMessage.ownerId());
            }
            case GET_BY_NAME -> {
                return getByName(rabbitCatMessage.name(), rabbitCatMessage.ownerId());
            }
            case GET_BY_ID -> {
                return getById(rabbitCatMessage.entityId(), rabbitCatMessage.ownerId());
            }
            case FILTER -> {
                return filterBy(rabbitCatMessage.color(), rabbitCatMessage.ownerId());
            }
        }
        return "";
    }

    private String filterBy(String color, Integer ownerId) throws JsonProcessingException {
        List<Cat> cats = catDao.findAllByOwnerId(ownerId).stream().filter(it -> it.getColor().equals(Color.valueOf(color.toUpperCase()))).toList();
        return objectMapper.writeValueAsString(cats.stream().map(this::map).toList());
    }

    private String getById(Integer entityId, Integer ownerId) throws JsonProcessingException {
        Cat cat = catDao.findByIdAndOwnerId(entityId, ownerId);
        return objectMapper.writeValueAsString(map(cat));
    }

    private String getByName(String name, Integer ownerId) throws JsonProcessingException {
        Cat cat = catDao.findByNameAndOwnerId(name, ownerId);
        return objectMapper.writeValueAsString(map(cat));
    }

    private String getAll(Integer ownerId) throws JsonProcessingException {
        List<Cat> cats = catDao.findAllByOwnerId(ownerId);
        return objectMapper.writeValueAsString(cats.stream().map(this::map).toList());
    }

    private CatDto map(Cat cat) {

        return cat == null ? CatDto.builder().build() : CatDto.builder()
                .id(cat.getId())
                .breed(cat.getBreed())
                .name(cat.getName())
                .birthday(cat.getBirthday())
                .color(cat.getColor())
                .build();
    }
}
