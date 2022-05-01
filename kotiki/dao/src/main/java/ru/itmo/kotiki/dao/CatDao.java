package ru.itmo.kotiki.dao;


import org.springframework.data.repository.CrudRepository;
import ru.itmo.kotiki.dao.entity.Cat;

import java.util.List;

public interface CatDao extends CrudRepository<Cat, Integer> {
    List<Cat> findAllByOwnerId(int ownerId);

    Cat findByIdAndOwnerId(int id, int ownerId);

    Cat findByName(String name);
}
