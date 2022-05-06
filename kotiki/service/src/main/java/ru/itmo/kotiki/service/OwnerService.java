package ru.itmo.kotiki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.OwnerDao;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.service.dto.OwnerDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerDao ownerDao;

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

    private OwnerDto map(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .birthday(owner.getBirthday())
                .build();
    }
}
