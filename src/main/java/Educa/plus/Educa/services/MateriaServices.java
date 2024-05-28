package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.materia.CadastroMateriaDTO;
import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.materia.RespostaMateriasDTO;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MateriaServices {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AtividadesRepository atividadesRepository;

    @Autowired
    private ConteudoExtraRepository conteudoExtraRepository;
    

    public ResponseEntity adicionaMateria(CadastroMateriaDTO data) {
        String materia = data.materia().toUpperCase();
        if(materiaRepository.findByMateriaNome(materia) != null)return ResponseEntity.badRequest().body(new ExceptMessage("Nome da materia ja em uso"));
        var materiaNova = new Materia(materia);
        materiaRepository.save(materiaNova);
        return ResponseEntity.ok().body(new ResponseMessage("Materia Adicionada com scuesso!"));
    }
    public ResponseEntity getAllMaterias(){
        if(materiaRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não Existem Materias Cadastradas!"));

        List<RespostaMateriasDTO> listaOfMaterias = materiaRepository.findAll().stream().map(RespostaMateriasDTO::new).toList();

        return ResponseEntity.ok().body(listaOfMaterias);
    }

    public ResponseEntity getMateriaPorNome(String materia){
        if(materiaRepository.findAllByMateriaNomeLike(materia).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não Existem Materias Cadastradas com o nome:"+materia));
       var materiaBusca = materiaRepository.getReferenceByMateriaNomeLike(materia);

       return ResponseEntity.ok().body(new RespostaMateriasDTO(materiaBusca));
    }

    public ResponseEntity deleteMateria(Long materiaId){
        if(materiaRepository.findById(materiaId).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Materia não encontrada!"));
        Materia materia = materiaRepository.getReferenceById(materiaId);
        if(!atividadesRepository.findAllByMateria(materia).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Materia Indicada possui Atividades!"));
        materiaRepository.deleteById(materiaId);
        return ResponseEntity.ok().body(new RespostaMateriasDTO(materia));
    }

    public ResponseEntity getMateriasContainsAtividade() {
        if(atividadesRepository.findAll().isEmpty()) return ResponseEntity.badRequest().body(new ExceptMessage("Não ha Atividades cadastradas!"));
        List<Materia> listaMateriasComAtividade = new ArrayList<>();
        List<Long> listaDeMateriasId = atividadesRepository.getAllMatriasidDistinct();
        for(Long idNumber : listaDeMateriasId){
        listaMateriasComAtividade.add(materiaRepository.getReferenceById(idNumber));
        }
        return ResponseEntity.ok().body(listaMateriasComAtividade.stream().map(RespostaMateriasDTO::new));
    }

    public ResponseEntity getMateriasContainsContExtra() {
        if(atividadesRepository.findAll().isEmpty()) return ResponseEntity.badRequest().body(new ExceptMessage("Não ha Atividades cadastradas!"));
        List<Materia> listaMateriasComConteudoExtra = new ArrayList<>();
        List<Long> listaDeMateriasId = conteudoExtraRepository.getAllMatriasidDistinct();
        for(Long idNumber : listaDeMateriasId){
            listaMateriasComConteudoExtra.add(materiaRepository.getReferenceById(idNumber));
        }
        return ResponseEntity.ok().body(listaMateriasComConteudoExtra.stream().map(RespostaMateriasDTO::new));
    }

    public ResponseEntity getMateriasContainsNotas() {
        if(atividadesRepository.findAll().isEmpty()) return ResponseEntity.badRequest().body(new ExceptMessage("Não ha Atividades cadastradas!"));
        List<Materia> listaMateriasComNotas = materiaRepository.buscarMateriasQueTenhamNotas();
        if(listaMateriasComNotas.isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não tem Materias com Notas Postadas!"));
        return ResponseEntity.ok().body(listaMateriasComNotas.stream().map(RespostaMateriasDTO::new));
    }
}
