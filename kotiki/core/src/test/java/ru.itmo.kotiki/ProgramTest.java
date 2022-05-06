package ru.itmo.kotiki;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.itmo.kotiki.dao.CatDao;
import ru.itmo.kotiki.dao.OwnerDao;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.service.OwnerService;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {OwnerService.class, ObjectMapper.class})
class ProgramTest {

    @MockBean
    CatDao catDao;

    @MockBean
    OwnerDao ownerDao;

    @Autowired
    OwnerService ownerService;

    @Test
    public void contextCheck() {
        assertNotNull(ownerService);
    }

    @Test
    void ownerSave_InvokeDaoSave() {

        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));
        ownerService.saveOwner(daniil);
        Mockito.verify(ownerDao).save(eq(daniil));
    }

    @Test
    void findOwnerByName_ownerFinds() {

        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));
        Owner john = new Owner("john", Date.valueOf("2000-01-03"));

        when(ownerDao.findByName(eq(daniil.getName()))).thenReturn(daniil);
        when(ownerDao.findByName(eq(john.getName()))).thenReturn(john);

        assertEquals(john.getName(), ownerService.findOwnerByName("john").getName());
    }

    @Test
    void findAllUsers_AllUsersFinds() {

        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));
        Owner john = new Owner("john", Date.valueOf("2000-01-03"));

        when(ownerDao.findAll()).thenReturn(List.of(daniil, john));

        assertEquals(ownerService.findAllOwners().size(), 2);
    }
}