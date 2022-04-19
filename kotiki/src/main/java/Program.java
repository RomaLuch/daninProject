import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Color;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.service.ManagerService;
import ru.itmo.kotiki.service.OwnerService;

import java.sql.Date;

public class Program {

    public static void main(String[] args){

        //SETUP
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

        //PROGRAM LOGIC

        ownerService.takeCat(daniil, herald);

        for (Cat cat : ownerService.findFreeCats()){
            System.out.print(cat.getName());
        }

        //CLEANING DB
        for (Cat cat : managerService.findAllCats()) managerService.deleteCat(cat);
        for (Owner owner : ownerService.findAllOwners()) ownerService.deleteOwner(owner);
    }
}
