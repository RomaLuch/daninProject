package ru.itmo.kotiki.cpntroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotiki.service.OwnerService;
import ru.itmo.kotiki.service.dto.OwnerDto;

import java.util.List;

@Controller
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() {
        List<OwnerDto> owners = ownerService.findAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/owner")
    public ResponseEntity<OwnerDto> getByName(@RequestParam("name") String name) {
        OwnerDto owner = ownerService.findOwnerByName(name);
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getById(@PathVariable("id") Integer id) {
        OwnerDto owner = ownerService.findOwner(id);
        return ResponseEntity.ok(owner);
    }
}
