package ru.itmo.kotiki.cpntroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.MyUserDetails;
import ru.itmo.kotiki.service.dto.OperationType;
import ru.itmo.kotiki.service.dto.RabbitCatQuery;

import java.util.List;

@Controller
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<CatDto>> getAll() throws JsonProcessingException {
        MyUserDetails myUserDetails = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int ownerId = myUserDetails.getUser().getOwner().getId();

        RabbitCatQuery message = new RabbitCatQuery(OperationType.GET_ALL, ownerId, null, null, null);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("catQueue", objectMapper.writeValueAsString(message));

        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, new TypeReference<>() {}));
    }

    @GetMapping("/cat")
    public ResponseEntity<CatDto> getByName(@RequestParam("name") String name) throws JsonProcessingException {
        MyUserDetails myUserDetails = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int ownerId = myUserDetails.getUser().getOwner().getId();

        RabbitCatQuery message = new RabbitCatQuery(OperationType.GET_BY_NAME, ownerId, null, name, null);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("catQueue", objectMapper.writeValueAsString(message));

        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, CatDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable("id") Integer id) throws JsonProcessingException {
        MyUserDetails myUserDetails = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int ownerId = myUserDetails.getUser().getOwner().getId();

        RabbitCatQuery message = new RabbitCatQuery(OperationType.GET_BY_ID, ownerId, id, null, null);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("catQueue", objectMapper.writeValueAsString(message));

        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, CatDto.class));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CatDto>> filter(@RequestParam("color") String color) throws JsonProcessingException {
        MyUserDetails myUserDetails = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int ownerId = myUserDetails.getUser().getOwner().getId();

        RabbitCatQuery message = new RabbitCatQuery(OperationType.FILTER, ownerId, null, null, color);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("catQueue", objectMapper.writeValueAsString(message));

        return ResponseEntity.ok(objectMapper.readValue(rabbitResponse, new TypeReference<>() {}));

    }
}
