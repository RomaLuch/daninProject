package ru.itmo.kotiki.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@Setter
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

    public Owner() {
    }

    public Owner(String name, Date birthday){
        this.name = name;
        this.birthday = birthday;
    }

    public void addCat(Cat cat){
        cat.setOwner(this);
        cats.add(cat);
    }

    public void removeCat(Cat cat){
        cats.remove(cat);
    }

}

