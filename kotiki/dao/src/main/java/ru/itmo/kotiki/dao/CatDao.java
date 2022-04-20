package ru.itmo.kotiki.dao;


import org.springframework.data.repository.CrudRepository;
import ru.itmo.kotiki.dao.entity.Cat;

public interface CatDao extends CrudRepository<Cat, Integer> {
    Cat findByName(String name);
}
