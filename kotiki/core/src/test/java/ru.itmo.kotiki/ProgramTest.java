package ru.itmo.kotiki;

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
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Color;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.service.CatService;
import ru.itmo.kotiki.service.OwnerService;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {OwnerService.class, CatService.class})
class ProgramTest {

    @MockBean
    CatDao catDao;

    @MockBean
    OwnerDao ownerDao;

    @Autowired
    OwnerService ownerService;

    @Autowired
    CatService catService;

    @Test
    public void contextCheck() {
        assertNotNull(ownerService);
        assertNotNull(catService);
    }

    @Test
    void saveCat_InvokeDaoSave() {

        Cat herald = new Cat();
        herald.setName("herald");
        herald.setBirthday(Date.valueOf("2020-01-01"));
        herald.setBreed("scotland");
        herald.setColor(Color.GREY);

        catService.saveCat(herald);
        Mockito.verify(catDao).save(eq(herald));
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
    void findCatByName_catFinds() {

        Cat herald = new Cat();
        herald.setName("herald");
        herald.setBirthday(Date.valueOf("2020-01-01"));
        herald.setBreed("scotland");
        herald.setColor(Color.GREY);

        Cat frederic = new Cat();
        frederic.setName("frederic");
        frederic.setBirthday(Date.valueOf("2019-08-08"));
        frederic.setBreed("england");
        frederic.setColor(Color.BLACK);

        when(catDao.findByName(eq(herald.getName()))).thenReturn(herald);
        when(catDao.findByName(eq(frederic.getName()))).thenReturn(frederic);

        assertEquals(frederic.getName(), catService.findCatByName("frederic").getName());
    }

    @Test
    void findAllCats_AllCatsFinds() {

        Cat herald = new Cat();
        herald.setName("herald");
        herald.setBirthday(Date.valueOf("2020-01-01"));
        herald.setBreed("scotland");
        herald.setColor(Color.GREY);

        Cat frederic = new Cat();
        frederic.setName("frederic");
        frederic.setBirthday(Date.valueOf("2019-08-08"));
        frederic.setBreed("england");
        frederic.setColor(Color.BLACK);

        Cat ramses = new Cat();
        ramses.setName("ramses");
        ramses.setBirthday(Date.valueOf("2021-05-05"));
        ramses.setBreed("african");
        ramses.setColor(Color.WHITE);

        when(catDao.findAll()).thenReturn(List.of(herald, frederic, ramses));

        assertEquals(catService.findAllCats().size(), 3);
    }

    @Test
    void findAllUsers_AllUsersFinds() {

        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));
        Owner john = new Owner("john", Date.valueOf("2000-01-03"));

        when(ownerDao.findAll()).thenReturn(List.of(daniil, john));

        assertEquals(ownerService.findAllOwners().size(), 2);
    }
}