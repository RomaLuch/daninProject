package ru.itmo.kotiki.cpntroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.kotiki.cpntroller.dto.AuthUser;
import ru.itmo.kotiki.cpntroller.dto.OperationType;
import ru.itmo.kotiki.cpntroller.dto.RabbitOwnerMessage;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCreateResponse> getByName(@RequestBody AuthUser user) throws JsonProcessingException {
        RabbitOwnerMessage message = new RabbitOwnerMessage(OperationType.CREATE, null, null, user);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("ownerQueue", objectMapper.writeValueAsString(message));
        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, UserCreateResponse.class));
    }

}
