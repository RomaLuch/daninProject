package ru.itmo.kotiki.cpntroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotiki.cpntroller.dto.OperationType;
import ru.itmo.kotiki.cpntroller.dto.OwnerDto;
import ru.itmo.kotiki.cpntroller.dto.RabbitOwnerMessage;

import java.util.List;

@Controller
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() throws JsonProcessingException {
        RabbitOwnerMessage message = new RabbitOwnerMessage(OperationType.GET_ALL, null, null, null);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("ownerQueue", objectMapper.writeValueAsString(message));

        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, new TypeReference<>() {}));

    }

    @GetMapping("/owner")
    public ResponseEntity<OwnerDto> getByName(@RequestParam("name") String name) throws JsonProcessingException {
        RabbitOwnerMessage message = new RabbitOwnerMessage(OperationType.GET_BY_NAME, null, name, null);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("ownerQueue", objectMapper.writeValueAsString(message));

        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, OwnerDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getById(@PathVariable("id") Integer id) throws JsonProcessingException {
        RabbitOwnerMessage message = new RabbitOwnerMessage(OperationType.GET_BY_ID, id, null, null);

        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("ownerQueue", objectMapper.writeValueAsString(message));

        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, OwnerDto.class));
    }
}
