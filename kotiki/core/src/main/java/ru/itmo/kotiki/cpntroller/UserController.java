package ru.itmo.kotiki.cpntroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.kotiki.service.OwnerService;
import ru.itmo.kotiki.service.dto.OwnerDto;

@Controller
@RequestMapping("/owners")
@RequiredArgsConstructor
public class UserController {

    private final OwnerService ownerService;

    @PostMapping("/user")
    public ResponseEntity<OwnerDto> getByName(@RequestBody() UserDto user) {
        OwnerDto owner = ownerService.findOwnerByName(user.getName());
        return ResponseEntity.ok(owner);
    }

}
