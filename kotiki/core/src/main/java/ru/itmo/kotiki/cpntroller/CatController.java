package ru.itmo.kotiki.cpntroller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ru.itmo.kotiki.service.CatService;
import ru.itmo.kotiki.service.dto.CatDto;
import ru.itmo.kotiki.service.dto.MyUserDetails;
import ru.itmo.kotiki.service.dto.OperationType;
import ru.itmo.kotiki.service.dto.RabbitQuery;

import java.util.List;

@Controller
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatController {

    private final CatService catService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<CatDto>> getAll() throws JsonProcessingException {
        MyUserDetails myUserDetails = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int ownerId = myUserDetails.getUser().getOwner().getId();
        RabbitQuery message = new RabbitQuery(OperationType.GET_ALL, ownerId, null);
        rabbitTemplate.convertAndSend("myQueue", objectMapper.writeValueAsString(message));
        List<CatDto> allCats = catService.findAllCatsByOwnerId(ownerId);
        return ResponseEntity.ok(allCats);
    }

    @GetMapping("/cat")
    public ResponseEntity<CatDto> getByName(@RequestParam("name") String name) {
        CatDto cat = catService.findCatByName(name);
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable("id") Integer id) {
        MyUserDetails myUserDetails = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int ownerId = myUserDetails.getUser().getOwner().getId();

        CatDto cat = catService.findCatByIdAndOwnerId(id, ownerId);
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CatDto>> filter(@RequestParam("color") String color) {
        MyUserDetails myUserDetails = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int ownerId = myUserDetails.getUser().getOwner().getId();

        List<CatDto> filteredCats = catService.findAllCatsByColorAndOwnerId(color, ownerId);
        return ResponseEntity.ok(filteredCats);
    }
}
