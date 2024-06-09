package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.atividades.*;
import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.usuario.UserRole;
import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtividadesServices {

    @Autowired
    private  AtividadesRepository atividadesRepository;

    @Autowired
    private UserRepository professorRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private NotasRepository notasRepository;


    public ResponseEntity createAtividade(CadastroAtividadeDTO data){
        if(professorRepository.findById(data.professor()).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Professor nao Encontrado"));
        if(materiaRepository.findByMateriaNomeLike(data.materia())==null)return ResponseEntity.badRequest().body(new ExceptMessage("Materia: "+data.materia()+" nao Encontrada!"));
        Materia materia = materiaRepository.getReferenceByMateriaNome(data.materia());
        Usuario professor = professorRepository.getReferenceById(data.professor());
        if(professor.getRole()!= UserRole.STAFF)return ResponseEntity.badRequest().body(new ExceptMessage("Usuario Informado nao é um professor"));
        if(!professor.getMateria().getMateriaNome().equalsIgnoreCase(materia.getMateriaNome()))return ResponseEntity.badRequest().body(new ExceptMessage("Professor Informado é de outra materia!"));
        Atividades atividadeNova;
        if(data.tipoAtividade()== TipoAtividade.MULTIPLA_ESCOLHA){
            if(data.resposta().isBlank())return ResponseEntity.badRequest().body(new ExceptMessage("Deve ser informado a Resposta da questao de multipla escolha"));
            if(data.questoes().isBlank())return ResponseEntity.badRequest().body(new ExceptMessage("Deve ser informado as Opçoes de Resposta da questao de multipla escolha"));
            atividadeNova = new Atividades(data.titulo(),data.tipoAtividade(), data.enunciado(), data.questoes().toUpperCase(), data.resposta().toUpperCase(), professor,materia);
        }else{
            if(data.questoes()!=null && !data.questoes().isBlank())return ResponseEntity.badRequest().body(new ExceptMessage("Questao descritiva nao pode conter questoes de multipla escolha"));
            if(!data.resposta().isBlank())return ResponseEntity.badRequest().body(new ExceptMessage("Questao descritiva nao pode conter respostas de multipla escolha"));
            if(data.enunciado().isBlank())return ResponseEntity.badRequest().body(new ExceptMessage("Deve ser informado um enunciado para Questao descritiva!"));
            atividadeNova = new Atividades(data.titulo(),data.tipoAtividade(), data.enunciado(), null, null, professor,materia);

        }
        atividadesRepository.save(atividadeNova);
        return ResponseEntity.ok().body(new RespostaAtividadeDTO(atividadeNova));

    }

    public ResponseEntity showAllAtividades(){
        if(atividadesRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não há Atividades Postadas"));
        List<RespostaAtividadeDTO> listaDeAtividades = atividadesRepository.findAll().stream().map(RespostaAtividadeDTO::new).toList();
        return ResponseEntity.ok().body(listaDeAtividades);
    }
    public ResponseEntity showAtividadesPorId(String id){
        if(atividadesRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não há Atividades Postadas"));
        if(atividadesRepository.findById(id).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não há Atividades Postadas com esse Id"));
        Atividades atividadeEncontrada = atividadesRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new RespostaAtividadeDTO(atividadeEncontrada));
    }

    public ResponseEntity removeAtividade(String id) {
        if (atividadesRepository.findById(id).isEmpty()) return ResponseEntity.badRequest().body(new ExceptMessage("Atividade nao encontrada"));
        Atividades atividade = atividadesRepository.getReferenceById(id);
        if(!feedbackRepository.findAllByAtividade(atividade).isEmpty()){
            feedbackRepository.deleteByAtividade(atividade);
        }
        if(!respostaRepository.findAllByAtividade(atividade).isEmpty()) {
            List<RespostaAtividade> listaDeRespostaDaAtividade = respostaRepository.findAllByAtividade(atividade);
            for (RespostaAtividade atividadeACH : listaDeRespostaDaAtividade) {
                if (notasRepository.findByRespostaAtividade(atividadeACH) != null) {
                    notasRepository.deleteAllByRespostaAtividade(atividadeACH);
                }
            }
            if (!respostaRepository.findAllByAtividade(atividade).isEmpty()) {
                respostaRepository.deleteByAtividade(atividade);
            }
        }
        atividadesRepository.deleteById(atividade.getIdAtividade());
        return ResponseEntity.ok().body(new ResponseMessage("Atividades de ID: "+ id+" e todas as dependencias de respostas e notas excluidas com sucesso!"));

    }
    public ResponseEntity updateAtividade(UpdateAtividadeDTO data){
        if(atividadesRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não há Atividades Postadas"));
        if(atividadesRepository.findById(data.idAtividade()).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não há Atividades Postadas com esse Id"));
        Atividades atividadeParaUpdate = atividadesRepository.getReferenceById(data.idAtividade());

        if(atividadeParaUpdate.getTipoAtividade()==TipoAtividade.MULTIPLA_ESCOLHA){
            if(!data.titulo().isBlank()){
                atividadeParaUpdate.setTitulo(data.titulo());
            }
            if(!data.enunciado().isBlank()){
                atividadeParaUpdate.setEnunciado(data.enunciado());
            }
            if(data.questoes()!=null && !data.questoes().isBlank()){
                atividadeParaUpdate.setQuestoes(data.questoes());
            }
            if(!data.resposta().isBlank()){
                atividadeParaUpdate.setRespostaCerta(data.resposta());
            }
        }else{
            if(!data.titulo().isBlank()){
                atividadeParaUpdate.setTitulo(data.titulo());
            }
            if(!data.enunciado().isBlank()){
                atividadeParaUpdate.setEnunciado(data.enunciado());
            }

        }

        var atividadeUpdated = atividadesRepository.getReferenceById(data.idAtividade());

        return ResponseEntity.ok().body(new RespostaAtividadeDTO(atividadeUpdated));
    }

    public ResponseEntity buscaDataDasAtividades(){
        List<DatasDTO> listaDeDatasDasAtividades = atividadesRepository.buscaDataOrdenandoPorDia().stream().map(DatasDTO::new).toList();
        return ResponseEntity.ok().body(listaDeDatasDasAtividades);
    }


}

