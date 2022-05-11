package ru.itmo.kotiki.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dao.entity.Role;
import ru.itmo.kotiki.dao.entity.Roles;

@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {

    Role findByName(Roles name);
}
