package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private List<Cheese> cheeses;

    public List<Cheese> getCheeses() { return cheeses; }

    public void setCheeses(List<Cheese> cheeses) { this.cheeses = cheeses; }

    public Menu(){}

    public Menu(String name){this.name = name; }

    public void addCheese(Cheese cheese){this.cheeses.add(cheese);}

    public void removeCheese(Cheese cheese){ this.cheeses.remove(cheese);}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getId() { return id; }

}
