package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.notas_das_respostas.PostaNotaDTO;
import Educa.plus.Educa.services.NotasServices;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("atividade/nota")
public class NotaController {
    @Autowired
    private NotasServices notasServices;


    @GetMapping("/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAllNotas(){
        return notasServices.showAllNotas();
    }

    @GetMapping("/{userId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showNotasByAlunoId(@PathVariable Long userId){
    return notasServices.showAllNotasByUser(userId);
    }
    @GetMapping("/materia/{materia}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAllNotasByProfessorMateria(@PathVariable String materia){
        return notasServices.showAllNotasByProfessorMateria(materia);
    }

    @PostMapping("/add/{respostaId}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity postaNotaEmResposta(@PathVariable String respostaId, @RequestBody @Valid PostaNotaDTO data){
        return notasServices.postaNotaEmResposta(respostaId, data);
    }

    @PutMapping("/update/{notaId}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity updateNota(@PathVariable Long notaId, @RequestBody PostaNotaDTO data){
        return notasServices.updateNotas(notaId, data);
    }
}
