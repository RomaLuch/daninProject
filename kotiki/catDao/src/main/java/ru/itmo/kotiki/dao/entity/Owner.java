package ru.itmo.kotiki.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@Data
@NoArgsConstructor
@ToString(exclude = "cats")
@EqualsAndHashCode
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private Date birthday;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Cat> cats = new ArrayList<>();

    public Owner(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public void addCat(Cat cat) {
        cat.setOwner(this);
        cats.add(cat);
    }

    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

}

