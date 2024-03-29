package com.pack.fiaraoccaz.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pack.fiaraoccaz.dao.FavorisDao;
import com.pack.fiaraoccaz.entity.Token;
import com.pack.fiaraoccaz.entity.User;
import com.pack.fiaraoccaz.model.Favoris;
import com.pack.fiaraoccaz.repository.TokenRepository;
import com.pack.fiaraoccaz.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/favoris")
public class FavorisController {
    private FavorisDao favorisDao;
    @Autowired
    private TokenRepository tokenRe ; 
    @Autowired
    private UserService userService;

    public FavorisController(FavorisDao favorisDao){
        this.favorisDao = favorisDao;
    }

    @PostMapping("/{token}/add/{id}")
    public String save(@RequestBody Favoris fav, @PathVariable("token") String token ,@PathVariable("id") String idU){
        Token tok = tokenRe.findIdUtilsateurFromToken(token); 
        Long id =Long.valueOf(idU) ;

        User user = userService.findUser(id);
        if(tok!=null && tok.isValid(id) && user.getEtat()==5){
            favorisDao.save(fav);
            return "Réussie";
        }
        return "Vous ne pouvez pas faire ce requête";
    }

    @GetMapping("/{token}/add/{id}/{idannonce}")
    public int findByUserAndAnnonce(@PathVariable("token") String token ,@PathVariable("id") String idU, @PathVariable("idannonce") int idannonce){
        Token tok = tokenRe.findIdUtilsateurFromToken(token); 
        Long id =Long.valueOf(idU) ;
        int iduser = id.intValue();
        User user = userService.findUser(id);
        if(tok!=null && tok.isValid(id) && user.getEtat()==10){
            int result = favorisDao.findByUserAndAnnonce(iduser, idannonce);
            return result;
        }
        return 0;
    }

    @GetMapping("/{token}/add/{id}")
    public List<Favoris> findAllByIduser(@PathVariable("token") String token ,@PathVariable("id") String idU){
        Token tok = tokenRe.findIdUtilsateurFromToken(token); 
        Long id =Long.valueOf(idU) ;
        int iduser = id.intValue();
        User user = userService.findUser(id);
        if(tok!=null && tok.isValid(id) && user.getEtat()==5){
            List<Favoris> result = favorisDao.findAllByIduser(iduser);
            return result;
        }
        return new ArrayList<>();
    }
}
