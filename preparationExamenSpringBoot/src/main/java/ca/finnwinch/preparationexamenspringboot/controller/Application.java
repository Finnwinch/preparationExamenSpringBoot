package ca.finnwinch.preparationexamenspringboot.controller;

import ca.finnwinch.preparationexamenspringboot.entities.Role;
import ca.finnwinch.preparationexamenspringboot.entities.Utilisateur;
import ca.finnwinch.preparationexamenspringboot.repos.RoleRepository;
import ca.finnwinch.preparationexamenspringboot.repos.UtilisateurRepository;
import ca.finnwinch.preparationexamenspringboot.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller public class Application {
    @Autowired public UtilisateurService utilisateurService;
    @Autowired public RoleRepository roleRepository;
    @Autowired public UtilisateurRepository utilisateurRepository;

    @GetMapping("") public String index(
            HttpSession session) {
        if (session.getAttribute("utilisateur") != null) {return "redirect:/home";}
        return "index" ;
    }
    @PostMapping("/utilisateur/connexion") public String connexion(
            HttpSession session,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {
            redirectAttributes.addAttribute("message","messageTestPd!") ;
        if (session.getAttribute("utilisateur") != null) {
            return "redirect:/home";
        }
        if(utilisateurService.getUtilisateurByNomAndPassword(username, password) != null) {
            Utilisateur user = utilisateurService.getUtilisateurByNomAndPassword(username, password) ;
            session.setAttribute("utilisateur", user);
            return "redirect:/home" ;
        }
        return "index" ;
    }
    @GetMapping("/utilisater/addPage") public String addPage(
            Model model,
            HttpSession session) {
        model.addAttribute("operation", "add");
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        model.addAttribute("utilisateur", user);
        return "utilisateurManagememt" ;
    }
    @Value("${upload-dir}") private String uploadDir;
    @PostMapping("/utilisater/add") public String add(
            Model model,
            HttpSession session,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("validePassword") String validePassword,
            @RequestParam("Courriel") String Courriel,
            @RequestParam("profil") MultipartFile profilImage,
            @RequestParam(value = "Administrateur", required = false) String Administrateur,
            @RequestParam(value = "Utilisateur", required = false) String Utilisateur,
            @RequestParam(value = "Visiteur", required = false) String Visiteur) {
        if (!password.equals(validePassword)) {model.addAttribute("error","password not patch"); return "redirect:/home" ; }
        String fileName = "";
        try {
            File convFile = new File(uploadDir,profilImage.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream is = profilImage.getInputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = is.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            is.close();
            fileName = convFile.getName() ;
        } catch (IOException e) {
            System.out.println("problème de récupération de l'image");
        }
        Utilisateur user = new Utilisateur();
        user.setNom(username.split(" ")[0]);
        user.setPrenom(username.split(" ")[1]);
        user.setPassword(password);
        user.setEmail(Courriel);
        List<Role> roles = new ArrayList<>() ;
        if (Visiteur != null) {
            Role role = roleRepository.findRoleByName("Visiteur") ;
            System.out.println(role.getId() + role.getName());
            roles.add(roleRepository.findRoleByName("Visiteur")) ;
        }
        if (Administrateur != null) {
            roles.add(roleRepository.findRoleByName("Administrateur")); ;
        }
        if (Utilisateur != null) {
            roles.add(roleRepository.findRoleByName("Utilisateur")) ;
        }
        user.setRoles(roles);
        user.setImage(fileName.isBlank() ? "default.png" : fileName);
        utilisateurService.insertUtilisateur(user);
        return "redirect:/home";
    }
    @PostMapping("utilisatet/addwithTH") public String addwithTH(
            @Validated Utilisateur utilisateur,
            BindingResult bindingResult,
            Model model) {
            if(bindingResult.hasErrors()){
                model.addAttribute("operation", "add");
                return "redirect:/utilisateurManagement";
            }
            utilisateurRepository.save(utilisateur) ;
        return "redirect:/home" ;
    }
    @GetMapping("/utilisater/removePage") public String removePage(
            Model model,
            HttpSession session) {
        model.addAttribute("operation", "remove");
        model.addAttribute("utilisateur", session.getAttribute("utilisateur"));
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "utilisateurManagememt" ;
    }
    @PostMapping("/utilisateur/remove") public String remove(
            Model model,
            HttpSession session,
            @RequestParam("utilisateur") Utilisateur user
    ) {
        utilisateurRepository.deleteById(user.getId());
        return "redirect:/home" ;
    }
    @GetMapping("/utilisater/updatePage") public String updatePage(
            Model model,
            HttpSession session) {
        model.addAttribute("operation", "update");
        model.addAttribute("utilisateur", session.getAttribute("utilisateur"));
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "utilisateurManagememt" ;
    }
    @GetMapping("/home") public String home(
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        if (user != null) {
            ArrayList<Utilisateur> utilisateurs ;
            utilisateurs = utilisateurService.getAllUtilisateurs() ;
            model.addAttribute("utilisateur", user);
            model.addAttribute("utilisateurs", utilisateurs);
            redirectAttributes.getAttribute("message") ;
            return "home";
        } else {
            return "redirect:/index";
        }
    }
    @GetMapping("/utilisateur/deconnexion") public String deconnexion(
            Model model,
            HttpSession session) {
        session.removeAttribute("utilisateur");
        return "index" ;
    }
    @PostMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> getUserDetails(@PathVariable("id") Integer userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElse(null);
        return ResponseEntity.ok().body(utilisateur);
    }
    @PostMapping("/utilisateur/update/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable("id") Integer userId) {
        return ResponseEntity.ok().body("Utilisateur mis à jour avec succès");
    }

}