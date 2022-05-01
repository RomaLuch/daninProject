package ru.itmo.kotiki.cpntroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.kotiki.service.UserService;
import ru.itmo.kotiki.service.dto.AuthUser;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getByName(@RequestBody AuthUser user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

}
