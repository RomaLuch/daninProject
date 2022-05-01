package ru.itmo.kotiki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.RoleDao;
import ru.itmo.kotiki.dao.UserDao;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.entity.Roles;
import ru.itmo.kotiki.dao.entity.User;
import ru.itmo.kotiki.service.dto.AuthUser;

@Service
@RequiredArgsConstructor
public class UserService {
    private final OwnerService ownerService;
    private final UserDao userDao;
    private final RoleDao roleDao;

    public void createUser(AuthUser user) {
        Owner owner = ownerService.saveOwner(new Owner(user.getFullName(), user.getBirthday()));
        userDao.save(new User(user.getName(),
                user.getPassword(),
                user.isEnabled(),
                roleDao.findByName(Roles.valueOf(user.getRole())),
                owner));
    }
}
