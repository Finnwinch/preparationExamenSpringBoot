package ca.finnwinch.preparationexamenspringboot.service;

import ca.finnwinch.preparationexamenspringboot.entities.Role;
import ca.finnwinch.preparationexamenspringboot.entities.Utilisateur;
import ca.finnwinch.preparationexamenspringboot.repos.RoleRepository;
import ca.finnwinch.preparationexamenspringboot.repos.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private RoleRepository roleRepository;
    public ArrayList<Utilisateur> getAllUtilisateurs() {
        return (ArrayList<Utilisateur>) utilisateurRepository.findAll();
    }
    public Utilisateur getUtilisateurByNomAndPassword(String nom, String password) {
        return utilisateurRepository.findUtilisateurByNomAndPassword(nom,password) ;
    }
    public void insertUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }
    public List<Role>getAllRoles(Integer id){
        return null ;
    }
}
