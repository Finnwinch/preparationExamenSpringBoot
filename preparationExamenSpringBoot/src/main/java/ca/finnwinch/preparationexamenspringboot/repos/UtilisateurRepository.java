package ca.finnwinch.preparationexamenspringboot.repos;

import ca.finnwinch.preparationexamenspringboot.entities.Role;
import ca.finnwinch.preparationexamenspringboot.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Integer> {
    public Utilisateur findUtilisateurByNomAndPassword(String nom, String password);
}