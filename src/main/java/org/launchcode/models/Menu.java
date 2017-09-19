package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=25)
    private String name;

    @ManyToMany
    private List<Cheese> cheeses = new ArrayList<>();

    public Menu(){}

    public Menu(String name){this.name = name; }

    public void addCheese(Cheese cheese){this.cheeses.add(cheese);}

    public String getName() { return name; }

    public int getId() { return id; }

    public List<Cheese> getCheeses() { return cheeses; }
}
