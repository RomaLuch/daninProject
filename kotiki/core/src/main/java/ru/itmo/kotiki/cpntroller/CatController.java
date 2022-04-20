package ru.itmo.kotiki.cpntroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotiki.service.CatService;
import ru.itmo.kotiki.service.dto.CatDto;

import java.util.List;

@Controller
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatController {

    private final CatService catService;

    @GetMapping
    public ResponseEntity<List<CatDto>> getAll() {
        List<CatDto> allCats = catService.findAllCats();
        return ResponseEntity.ok(allCats);
    }

    @GetMapping("/cat")
    public ResponseEntity<CatDto> getByName(@RequestParam("name") String name) {
        CatDto cat = catService.findCatByName(name);
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable("id") Integer id) {
        CatDto cat = catService.findCat(id);
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CatDto>> filter(@RequestParam("color") String color) {
        List<CatDto> filteredCats = catService.findAllCatsBy(color);
        return ResponseEntity.ok(filteredCats);
    }
}
