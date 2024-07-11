package ca.finnwinch.preparationexamenspringboot.entities;

import jakarta.persistence.*;

@Entity @Table(name="role")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    @Column(nullable = false) private String name;

    public Role() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Role(String name){
        this.name = name;
    }
}
