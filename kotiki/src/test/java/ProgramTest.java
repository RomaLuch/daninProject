import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Color;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.service.ManagerService;
import ru.itmo.kotiki.service.OwnerService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
class ProgramTest {

    @AfterEach
    void clean(){ //TEST DELETING OWNERS
        OwnerService ownerService = new OwnerService();
        ManagerService managerService = new ManagerService();
        for (Cat cat : managerService.findAllCats()) managerService.deleteCat(cat);
        for (Owner owner : ownerService.findAllOwners()) ownerService.deleteOwner(owner);
    }

    @Test
    void ownerTakesAndReturnCat_catTakenAndReturned(){

        OwnerService ownerService = new OwnerService();
        ManagerService managerService = new ManagerService();

        Cat herald = new Cat();
        herald.setName("herald");
        herald.setBirthday(Date.valueOf("2020-01-01"));
        herald.setBreed("scotland");
        herald.setColor(Color.GREY);

        managerService.saveCat(herald);

        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));

        ownerService.saveOwner(daniil);

        ownerService.takeCat(daniil, herald);

        assertEquals(daniil.getId(), herald.getOwner().getId());

        ownerService.returnCat(daniil, herald);

        assertNull(herald.getOwner());
    }

    @Test
    void findingFreeCats_freeCatsFinds(){
        OwnerService ownerService = new OwnerService();
        ManagerService managerService = new ManagerService();

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

        managerService.saveCat(herald);
        managerService.saveCat(frederic);

        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));

        ownerService.saveOwner(daniil);

        ownerService.takeCat(daniil, frederic);

        assertEquals(ownerService.findFreeCats().get(0), herald);
    }

    @Test
    void findOwnerByName_ownerFinds(){
        OwnerService ownerService = new OwnerService();

        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));
        Owner john = new Owner("john", Date.valueOf("2000-01-03"));

        ownerService.saveOwner(daniil);
        ownerService.saveOwner(john);

        assertEquals(john, ownerService.findOwnerByName("john"));
    }

    @Test
    void findCatByName_catFinds(){
        ManagerService managerService = new ManagerService();

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

        managerService.saveCat(herald);
        managerService.saveCat(frederic);

        assertEquals(frederic, managerService.findCatByName("frederic"));
    }

    @Test
    void findAllCatsOrUsers_AllCatsAndUsersFinds(){
        OwnerService ownerService = new OwnerService();
        ManagerService managerService = new ManagerService();

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

        managerService.saveCat(herald);
        managerService.saveCat(frederic);
        managerService.saveCat(ramses);


        Owner daniil = new Owner("daniil", Date.valueOf("2002-01-08"));
        Owner john = new Owner("john", Date.valueOf("2000-01-03"));

        ownerService.saveOwner(daniil);
        ownerService.saveOwner(john);

        assertEquals(ownerService.findAllOwners().size(), 2);
        assertEquals(managerService.findAllCats().size(), 3);
    }
}