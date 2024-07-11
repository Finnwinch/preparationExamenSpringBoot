package ca.finnwinch.preparationexamenspringboot.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="utilisateur")
public class Utilisateur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id ;
    @Column(nullable = false) private String nom ;
    @Column(nullable = false) private String prenom ;
    @Column(nullable = false) private String email ;
    @Column(nullable = false) private String password ;
    @ManyToMany
    @JoinTable(name="utilisateurs_roles",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    ) private List<Role> roles = new ArrayList<>();
    @Column(nullable = true) private String image ;
    public Utilisateur() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return (List<Role>) roles;
    }
    public void setRoles(List<Role> roles) { this.roles = roles; }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
