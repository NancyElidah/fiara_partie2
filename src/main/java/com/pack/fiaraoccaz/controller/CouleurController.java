package com.pack.fiaraoccaz.controller;

import com.pack.fiaraoccaz.service.CouleurService;
import com.pack.fiaraoccaz.entity.Couleur;
import com.pack.fiaraoccaz.entity.Token;
import com.pack.fiaraoccaz.entity.User;
import com.pack.fiaraoccaz.repository.TokenRepository;
import com.pack.fiaraoccaz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/couleurs")
public class CouleurController {

    private final CouleurService couleurService;
    @Autowired
    private TokenRepository tokenRe;
    @Autowired
    private UserService userService;

    @Autowired
    public CouleurController(CouleurService couleurService) {
        this.couleurService = couleurService;
    }

    @PostMapping("/{token}/create/{id}")
    public Couleur createCouleur(@RequestBody Couleur couleur,
                                 @PathVariable("token") String token,
                                 @PathVariable("id") String idU) throws Exception {
        Token tok = tokenRe.findIdUtilsateurFromToken(token);
        Long id = Long.valueOf(idU);

        User user = userService.findUser(id);
        if (tok != null && tok.isValid(id) && user.getEtat() == 10) {
            return couleurService.createCouleur(couleur);
        }
        return null; 
    }

    @GetMapping("/{token}/getAll/{id}")
    public List<Couleur> getAllCouleurs(@PathVariable("token") String token,
                                       @PathVariable("id") String idU) throws Exception {
        Token tok = tokenRe.findIdUtilsateurFromToken(token);
        Long id = Long.valueOf(idU);

        User user = userService.findUser(id);
        if (tok != null && tok.isValid(id) && user.getEtat() >= 5) {
            return couleurService.getAllCouleurs();
        }
        return null; 
    }

    @GetMapping("/getAll")
    public List<Couleur> getAllCouleurs() throws Exception {
            return couleurService.getAllCouleurs();
    }

    @GetMapping("/{token}/getById/{id}/{idU}")
    public Optional<Couleur> getCouleurById(@PathVariable Long id,
                                            @PathVariable("token") String token,
                                            @PathVariable("idU") String idU) throws Exception {
        Token tok = tokenRe.findIdUtilsateurFromToken(token);
        id = Long.valueOf(idU);

        User user = userService.findUser(id);
        if (tok != null && tok.isValid(id) && user.getEtat() == 10) {
            return couleurService.getCouleurById(id);
        }
        return Optional.empty(); 
    }

    @PutMapping("/{token}/update/{id}/{idU}")
    public Couleur updateCouleur(@PathVariable Long id,
                                 @RequestBody Couleur newCouleur,
                                 @PathVariable("token") String token,
                                 @PathVariable("idU") String idU) throws Exception {
        Token tok = tokenRe.findIdUtilsateurFromToken(token);
        id = Long.valueOf(idU);

        User user = userService.findUser(id);
        if (tok != null && tok.isValid(id) && user.getEtat() == 10) {
            return couleurService.updateCouleur(id, newCouleur);
        }
        return null; 
    }

    @DeleteMapping("/{token}/delete/{id}/{idU}")
    public void deleteCouleur(@PathVariable Long id,
                              @PathVariable("token") String token,
                              @PathVariable("idU") String idU) throws Exception {
        Token tok = tokenRe.findIdUtilsateurFromToken(token);
        id = Long.valueOf(idU);

        User user = userService.findUser(id);
        if (tok != null && tok.isValid(id) && user.getEtat() == 10) {
            couleurService.deleteCouleur(id);
        }
        
    }
}
