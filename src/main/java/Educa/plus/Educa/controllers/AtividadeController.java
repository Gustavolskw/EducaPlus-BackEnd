package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.atividades.CadastroAtividadeDTO;
import Educa.plus.Educa.domain.atividades.DatasRecebeDTO;
import Educa.plus.Educa.domain.atividades.UpdateAtividadeDTO;
import Educa.plus.Educa.services.AtividadesServices;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("atividade")
public class AtividadeController {

    @Autowired
    private AtividadesServices atividadesServices;

    @GetMapping("/list")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAll(){
        return atividadesServices.showAllAtividades();
    }

    @GetMapping("/list/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showById(@PathVariable String id){
        return atividadesServices.showAtividadesPorId(id);
    }

    @GetMapping("/list/done/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showAllDoneByUser(@PathVariable Long id){
        return atividadesServices.showAllAtividadesDoneByUser(id);
    }

    @GetMapping("/list/verification")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Boolean retornaSeAtividadeDone(@RequestParam(name = "atv") String atividadeId, @RequestParam(name = "usr") Long id){
        return atividadesServices.isAtividadeDone(atividadeId, id);
    }


    @PostMapping("/add")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity createAtividade(@RequestBody @Valid CadastroAtividadeDTO data){
        return atividadesServices.createAtividade(data);
    }

    @PutMapping("/update")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity updateAtividade(@RequestBody @Valid UpdateAtividadeDTO data){
        return atividadesServices.updateAtividade(data);
    }

    @DeleteMapping("/remove/{id}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity deleteAtividade(@PathVariable String id){
        return atividadesServices.removeAtividade(id);
    }


}
