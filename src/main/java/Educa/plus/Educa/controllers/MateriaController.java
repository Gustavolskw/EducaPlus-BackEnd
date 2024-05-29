package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.materia.CadastroMateriaDTO;
import Educa.plus.Educa.services.MateriaServices;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("materia")
public class MateriaController {
    @Autowired
    private MateriaServices materiaServices;


    @PostMapping("/add")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity cadastraMateria(@RequestBody @Valid CadastroMateriaDTO data){
       return  materiaServices.adicionaMateria(data);
    }
    @GetMapping("/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity seeAllMaterias(){
        return materiaServices.getAllMaterias();
    }

    @GetMapping("/{nome}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity seeAllMaterias(@PathVariable String nome ){
        return materiaServices.getMateriaPorNome(nome);
    }
    @GetMapping("/user/{userId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity getMateriaByUserId(@PathVariable Long userId ){
        return materiaServices.getMateriaPorUserId(userId);
    }

    @GetMapping("/atividade")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity seeAllMateriasHasAtividade(){
        return materiaServices.getMateriasContainsAtividade();
    }

    @GetMapping("/cont-extra")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity seeAllMateriasHasContExtra(){
        return materiaServices.getMateriasContainsContExtra();
    }

    @GetMapping("/notas")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity seeAllMateriasHasNotas(){
        return materiaServices.getMateriasContainsNotas();
    }

    @DeleteMapping("remove/{idMateria}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional
    public ResponseEntity deletaMatria(@PathVariable Long idMateria){
        return materiaServices.deleteMateria(idMateria);
    }
}
