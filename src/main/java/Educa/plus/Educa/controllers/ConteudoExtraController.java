package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.conteudo_extra.ConteudoExtra;
import Educa.plus.Educa.domain.conteudo_extra.ConteudoExtraPostDTO;
import Educa.plus.Educa.domain.conteudo_extra.UpdateConteudoExtraDTO;
import Educa.plus.Educa.services.ConteudoExtraServices;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("conteudo/extra")
public class ConteudoExtraController {

    @Autowired
    private ConteudoExtraServices conteudoExtraServices;

    @PostMapping("/add")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity adicionaConteudoExtra(@RequestBody @Valid ConteudoExtraPostDTO data){
        return conteudoExtraServices.adicionaConteudoExtra(data);
    }

    @GetMapping("/list")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAllContExtra(){
        return conteudoExtraServices.showAllContExtra();
    }

    @DeleteMapping("/remove/{id}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity removeConteudoExtra(@PathVariable String id){
        return conteudoExtraServices.removerConteudoExtra(id);
    }

    @PutMapping("/update")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity updateContExtra(@RequestBody @Valid UpdateConteudoExtraDTO data){
        return conteudoExtraServices.updateConteudoExtra(data);
    }


}
