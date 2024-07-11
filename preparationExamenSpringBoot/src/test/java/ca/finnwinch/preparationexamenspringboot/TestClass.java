package ca.finnwinch.preparationexamenspringboot;

import ca.finnwinch.preparationexamenspringboot.entities.Role;
import ca.finnwinch.preparationexamenspringboot.entities.Utilisateur;
import ca.finnwinch.preparationexamenspringboot.repos.RoleRepository;
import ca.finnwinch.preparationexamenspringboot.repos.UtilisateurRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(false)
public class TestClass {
    @Autowired UtilisateurRepository utilisateurRepository;
    @Autowired RoleRepository roleRepository;
    //@Autowired TestEntityManager testEntityManager;
    @Test @Order(1) public void CreateRoles() {
        Role role = new Role();
        role.setName("Administrateur");
        Role role2 = new Role() ;
        role2.setName("Utilistaeur");
        Role role3 = new Role() ;
        role3.setName("Visiteur");
        roleRepository.save(role);
        roleRepository.save(role2);
        roleRepository.save(role3);
    }
    @Test @Order(2) public void DeleteRoles() {
        roleRepository.deleteAll();
    }
    @Test @Order(3) public void CreateAdminAccount() {
        Utilisateur user = new Utilisateur();
        user.setNom("Bob");
        user.setPrenom("Éponge");
        user.setPassword("powel");
        user.setEmail("admin@support.ca");
        user.setImage("undefind");
        user.addRole(roleRepository.findById(1).orElseThrow());
        utilisateurRepository.save(user);
    }
    @Test @Order(4) public void DeleteAdminAccount() {
        utilisateurRepository.deleteById(1);
    }
}
