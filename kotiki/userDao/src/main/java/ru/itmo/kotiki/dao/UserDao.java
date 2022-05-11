package ru.itmo.kotiki.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dao.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByName(String name);
}
