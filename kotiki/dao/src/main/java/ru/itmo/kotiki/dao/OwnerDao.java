package ru.itmo.kotiki.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dao.entity.Owner;

@Repository
public interface OwnerDao extends CrudRepository<Owner, Integer> {

    Owner findByName(String name);
}
