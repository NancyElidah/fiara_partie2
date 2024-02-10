package com.pack.fiaraoccaz.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pack.fiaraoccaz.dao.V_Annonce_2Dao;
import com.pack.fiaraoccaz.entity.Token;
import com.pack.fiaraoccaz.entity.User;
import com.pack.fiaraoccaz.model.V_Annonce_2;
import com.pack.fiaraoccaz.repository.TokenRepository;
import com.pack.fiaraoccaz.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v_annonce_2")
public class V_Annonce_2Controller {
    private V_Annonce_2Dao vaDao;
    @Autowired
    private TokenRepository tokenRe ; 
    @Autowired
    private UserService userService;

    public V_Annonce_2Controller(V_Annonce_2Dao vaDao){
        this.vaDao = vaDao;
    }

    @GetMapping("/{token}/get/{idU}/{etat}/{status}")
    public List<V_Annonce_2> findAllByEtatAndStatus(@PathVariable("etat") int etat, @PathVariable("status") int status, @PathVariable("token") String token ,@PathVariable("idU") String idU){
        Token tok = tokenRe.findIdUtilsateurFromToken(token); 
        Long id =Long.valueOf(idU);

        User user = userService.findUser(id);
        if(tok!=null && tok.isValid(id) && user.getEtat()==10){
            return vaDao.findAllByEtatAndStatus(etat, status);
        }
        return new ArrayList<>();
    }
}