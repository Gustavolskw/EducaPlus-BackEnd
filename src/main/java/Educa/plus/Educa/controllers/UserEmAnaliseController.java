package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.materia.CadastroMateriaDTO;
import Educa.plus.Educa.domain.userAnalises.CadastroUsuarioEmAnaliseDTO;
import Educa.plus.Educa.services.UserEmAnaliseServices;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("newUser")
public class UserEmAnaliseController{

    @Autowired
    private UserEmAnaliseServices userEmAnaliseServices;


    @PostMapping("/register")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity cadastrarUsuarioEmAnalise(@RequestBody @Valid CadastroUsuarioEmAnaliseDTO data){
        return userEmAnaliseServices.createUserToAnalise(data);
    }
    @GetMapping("/analise/list")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity verListaParaAnalise(){
        return userEmAnaliseServices.showAllAnalisesUsers();
    }
    @PostMapping("/analise/aprove/{id}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity aprovarusuario(@PathVariable Long id){
        return userEmAnaliseServices.aprovaUserEmAnalise(id);
    }

    @DeleteMapping("/analise/remove/{id}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity desaprovaUsuario(@PathVariable Long id){
        return userEmAnaliseServices.desaprovaUsuario(id);
    }



}
