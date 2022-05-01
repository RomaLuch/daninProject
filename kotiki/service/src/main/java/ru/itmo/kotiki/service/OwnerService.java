package ru.itmo.kotiki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.CatDao;
import ru.itmo.kotiki.dao.OwnerDao;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.service.dto.OwnerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OwnerService {

    private final OwnerDao ownerDao;
    private final CatDao catDao;
    private final CatService catService;

    @Autowired
    public OwnerService(OwnerDao ownerDao, CatDao catDao, CatService catService) {
        this.ownerDao = ownerDao;
        this.catDao = catDao;
        this.catService = catService;
    }

    public OwnerDto findOwner(int id) {
        return map(ownerDao.findById(id).get());
    }

    public Owner saveOwner(Owner owner) {
        return ownerDao.save(owner);
    }

    public void updateOwner(Owner owner) {
        ownerDao.save(owner);
    }

    public List<OwnerDto> findAllOwners() {
        return StreamSupport.stream(ownerDao.findAll().spliterator(), false)
                .map(this::map)
                .collect(Collectors.toList());
    }

    public OwnerDto findOwnerByName(String name) {
        return map(ownerDao.findByName(name));
    }

    public List<Cat> findFreeCats() {
        List<Cat> result = new ArrayList<>();
        for (Cat cat : catDao.findAll()) {
            if (cat.getOwner() == null) {
                result.add(cat);
            }
        }
        return result;
    }

    public void takeCat(Owner owner, Cat cat) {
        owner.addCat(cat);
        this.updateOwner(owner);
        catService.updateCat(cat);
    }

    public void returnCat(Owner owner, Cat cat) {
        try {
            owner.removeCat(cat);
            cat.setOwner(null);
            this.updateOwner(owner);
            catService.updateCat(cat);
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    private OwnerDto map(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .birthday(owner.getBirthday())
                .build();
    }
}
