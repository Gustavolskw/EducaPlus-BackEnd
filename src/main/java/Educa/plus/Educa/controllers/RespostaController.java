package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.resolucao_atividade.CadastroDeRespostaDTO;
import Educa.plus.Educa.services.RespostaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("atividade/resposta")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;



    @GetMapping("/{idAtividade}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAllRespostasBytividade(@PathVariable String idAtividade){
        return respostaService.ShowAllRespostaByAtividade(idAtividade);
    }
    @GetMapping("/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAllRespostas(){
        return respostaService.ShowAllRespostas();
    }
    @GetMapping("/prof/{professorId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAllRespostasFromAtividadeByProfessor(@PathVariable @Valid Long professorId){
        return respostaService.ShowAllRespostasForProfessorId(professorId);
    }

    @PostMapping("/send/{idAtividade}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity enviaRespostaParaAtividade(@PathVariable String idAtividade, @RequestBody @Valid CadastroDeRespostaDTO data){
        return respostaService.enviarResposta(idAtividade, data);
    }

}
