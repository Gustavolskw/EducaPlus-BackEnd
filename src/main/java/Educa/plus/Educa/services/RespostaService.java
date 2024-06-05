package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.resolucao_atividade.CadastroDeRespostaDTO;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaResponseDTO;
import Educa.plus.Educa.domain.usuario.UserRole;
import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.repositories.AtividadesRepository;
import Educa.plus.Educa.repositories.RespostaRepository;
import Educa.plus.Educa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtividadesRepository atividadesRepository;


    public ResponseEntity ShowAllRespostaByAtividade(String atividadeId){
        if(atividadesRepository.findAll().isEmpty())return ResponseEntity.status(203).body(new ExceptMessage("Nenhuma atividade postada!"));
        if(atividadesRepository.findById(atividadeId).isEmpty())return ResponseEntity.status(203).body(new ExceptMessage("Nenhuma atividade econtrada pelo ID informado!"));
        if(respostaRepository.findAll().isEmpty())return ResponseEntity.status(203).body(new ExceptMessage("Nao foi encontrada nenhuma resposta para atividades!"));
        Atividades atividade = atividadesRepository.getReferenceById(atividadeId);
        if(respostaRepository.findAllByAtividade(atividade).isEmpty())return ResponseEntity.status(203).body(new ExceptMessage("Nenhuma resposta para essa atividade!"));
        List<RespostaResponseDTO> listaDeRespostaDaAtividade = respostaRepository.findAllByAtividade(atividade).stream().map(RespostaResponseDTO::new).toList();
        return ResponseEntity.ok().body(listaDeRespostaDaAtividade);
    }

    public ResponseEntity enviarResposta(String atividadeId, CadastroDeRespostaDTO data){
        if(atividadesRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Nenhuma atividade postada!"));
        Usuario user = userRepository.getReferenceById(data.usuario());
        Atividades atividade = atividadesRepository.getReferenceById(atividadeId);
        if(respostaRepository.findByAlunoAndAtividade(user, atividade) != null)return ResponseEntity.badRequest().body(new ExceptMessage("Usuario ja Respondeu a pergunta!"));
        if(atividadesRepository.findById(atividadeId).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Nenhuma atividade econtrada pelo ID informado!"));

        if(userRepository.findById(data.usuario()).isEmpty()) return ResponseEntity.badRequest().body(new ExceptMessage("não foi encontrado o usuario com esse ID!"));
        if(user.getRole()!= UserRole.USER){
            return ResponseEntity.badRequest().body(new ExceptMessage("Usuario encontrado não é um aluno!"));
        }
        RespostaAtividade novaResposta = new RespostaAtividade(atividade, data.resposta(), user);
        respostaRepository.save(novaResposta);
        return ResponseEntity.ok().body(new ResponseMessage("Resposta Enviada com sucesso"));
    }


    public ResponseEntity ShowAllRespostas() {
        if(respostaRepository.findAll().isEmpty())return ResponseEntity.status(404).body(new ExceptMessage("Nao foi encontrada nenhuma resposta para atividades!"));

        List<RespostaResponseDTO> todasRespostasDasAtividades = respostaRepository.buscarTodasRespostasSemNota().stream().map(RespostaResponseDTO::new).toList();
        return ResponseEntity.ok().body(todasRespostasDasAtividades);
    }

    public ResponseEntity ShowAllRespostasForProfessorId(Long professorId) {
        if(userRepository.findById(professorId).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("professor nao existe!"));
        Usuario user = userRepository.getReferenceById(professorId);
        if(user.getMateria().getIdMaterias() == null)return ResponseEntity.badRequest().body(new ExceptMessage("professor nao possui materia atrealada!"));
        List<RespostaResponseDTO> listaNotasDoProfessor = respostaRepository.buscaAtividadesDoProfessor(user.getMateria().getIdMaterias()).stream().map(RespostaResponseDTO::new).toList();
        return ResponseEntity.ok().body(listaNotasDoProfessor);
    }
}
