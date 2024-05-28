package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.notas_das_respostas.NotasAtividade;
import Educa.plus.Educa.domain.notas_das_respostas.PostaNotaDTO;
import Educa.plus.Educa.domain.notas_das_respostas.RespostaNotaDTO;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.usuario.UserRole;
import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.repositories.AtividadesRepository;
import Educa.plus.Educa.repositories.NotasRepository;
import Educa.plus.Educa.repositories.RespostaRepository;
import Educa.plus.Educa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotasServices {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtividadesRepository atividadesRepository;

    @Autowired
    private NotasRepository notasRepository;

    public ResponseEntity postaNotaEmResposta(String idReposta, PostaNotaDTO data){
        if(respostaRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não ha respostas postadas!"));
        if(userRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não ha usuarios cadastrados!"));
        if(userRepository.findById(data.avaliador()).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Avaliador Inexistente!"));
        if(respostaRepository.findById(idReposta).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não ha respostas postadas com o ID informado!"));
        RespostaAtividade respostaDoAluno = respostaRepository.getReferenceById(idReposta);
        if(notasRepository.findAllByRespostaAtividade(respostaDoAluno).isPresent())return ResponseEntity.badRequest().body(new ExceptMessage("Resposta de Atividade ja foi avaliada!"));
        Usuario avaliador = userRepository.getReferenceById(data.avaliador());
        if(avaliador.getRole()== UserRole.USER || avaliador.getRole()== UserRole.ADMIN )return ResponseEntity.badRequest().body(new ExceptMessage("Avaliador Indicado não é um professor"));
        NotasAtividade nota = new NotasAtividade(respostaDoAluno, data.nota(), avaliador);

        notasRepository.save(nota);

        return ResponseEntity.ok().body(new ResponseMessage("Nota postada com scuesso!"));
    }

    public ResponseEntity showAllNotasByUser(Long userId){
        if(userRepository.findById(userId).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Usuario nao encontrado!"));
        if(respostaRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("nenhuma resposta cadastrada"));
        if(notasRepository.findAll().isEmpty())return ResponseEntity.badRequest().body("Nenhuma nota encontrada!");
        var usuario = userRepository.getReferenceById(userId);
        if(notasRepository.pegarNotasDoAlunoPorId(usuario).isEmpty())return ResponseEntity.badRequest().body("nenhuma nota para o usuario informado!");
        List<RespostaNotaDTO> listaDeNotasAluno = notasRepository.pegarNotasDoAlunoPorId(usuario).stream().map(RespostaNotaDTO::new).toList();
        return ResponseEntity.ok().body(listaDeNotasAluno);
    }

    public ResponseEntity showAllNotas(){
        if(notasRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não ha notas postadas!"));
        List<RespostaNotaDTO>listaDeNotas = notasRepository.findAll().stream().map(RespostaNotaDTO::new).toList();
        return ResponseEntity.ok().body(listaDeNotas);
    }

    public ResponseEntity updateNotas(Long idNota, PostaNotaDTO data){
        if(userRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não ha usuarios cadastrados!"));
        if(userRepository.findById(data.avaliador()).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Avaliador Inexistente!"));
        if(notasRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não ha notas cadastradas!"));
        if(notasRepository.findById(idNota).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não ha notas cadastradas com o ID informado!"));
        var notaParaUpdate = notasRepository.getReferenceById(idNota);
        var avaliador = userRepository.getReferenceById(data.avaliador());
        if(avaliador.getRole()== UserRole.USER || avaliador.getRole()== UserRole.ADMIN )return ResponseEntity.badRequest().body(new ExceptMessage("Avaliador Indicado não é um professor"));
        if(data.nota()!=null){
            notaParaUpdate.setNota(data.nota());
            notaParaUpdate.setAvaliador(avaliador);
        }
        var notaAfterUpdate = notasRepository.getReferenceById(idNota);

        return ResponseEntity.ok().body(new RespostaNotaDTO(notaAfterUpdate));

    }





}
