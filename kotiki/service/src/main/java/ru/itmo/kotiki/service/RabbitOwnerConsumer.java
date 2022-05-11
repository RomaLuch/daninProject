package ru.itmo.kotiki.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.kotiki.dao.OwnerDao;
import ru.itmo.kotiki.dao.RoleDao;
import ru.itmo.kotiki.dao.UserDao;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.entity.Roles;
import ru.itmo.kotiki.dao.entity.User;
import ru.itmo.kotiki.service.dto.AuthUser;
import ru.itmo.kotiki.service.dto.OwnerDto;
import ru.itmo.kotiki.service.dto.RabbitOwnerMessage;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RabbitOwnerConsumer {

    private final OwnerDao ownerDao;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "ownerQueue")
    @Transactional
    public String dispatch(String message) throws JsonProcessingException {
        RabbitOwnerMessage rabbitOwnerMessage = objectMapper.readValue(message, RabbitOwnerMessage.class);
        switch (rabbitOwnerMessage.operationType()) {
            case GET_ALL -> {
                return getAll();
            }
            case GET_BY_NAME -> {
                return getByName(rabbitOwnerMessage.name());
            }
            case GET_BY_ID -> {
                return getById(rabbitOwnerMessage.entityId());
            }
            case CREATE -> {
                return create(rabbitOwnerMessage.user());
            }
        }
        return "";
    }

    private String create(AuthUser user) throws JsonProcessingException {

        try {
            userDao.save(new User(user.getName(),
                    user.getPassword(),
                    user.isEnabled(),
                    roleDao.findByName(Roles.valueOf(user.getRole())),
                    new Owner(user.getFullName(), user.getBirthday())));
        }
        catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":\"user not created\"}";
        }

        return objectMapper.writeValueAsString("{\"result\":\"user created\"}");
    }

    private String getById(Integer entityId) throws JsonProcessingException {

        OwnerDto owner = map(ownerDao.findById(entityId).orElse(new Owner()));
        return objectMapper.writeValueAsString(owner);
    }

    private String getByName(String name) throws JsonProcessingException {
        OwnerDto owner = map(ownerDao.findByName(name));
        return objectMapper.writeValueAsString(owner);

    }

    private String getAll() throws JsonProcessingException {
        List<OwnerDto> owners = StreamSupport.stream(ownerDao.findAll().spliterator(), false)
                .map(this::map)
                .toList();

        return objectMapper.writeValueAsString(owners);
    }

    private OwnerDto map(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .birthday(owner.getBirthday())
                .build();
    }
}
