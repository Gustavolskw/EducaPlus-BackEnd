package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.conteudo_extra.*;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.repositories.ConteudoExtraRepository;
import Educa.plus.Educa.repositories.MateriaRepository;
import Educa.plus.Educa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConteudoExtraServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ConteudoExtraRepository conteudoExtraRepository;

    public ResponseEntity adicionaConteudoExtra(ConteudoExtraPostDTO data) {
        if(materiaRepository.findByMateriaNome(data.materia())==null)return ResponseEntity.badRequest().body(new ExceptMessage("materia: "+data.materia()+" inexistente!"));
        if(userRepository.findById(data.professor()).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Professor não Encontrado!"));
        var professor = userRepository.getReferenceById(data.professor());
        if(professor.getMateria().getMateriaNome().isBlank()){
            return ResponseEntity.badRequest().body(new ExceptMessage("usuario Indicado nao é um professor!"));
        }
        var matreria = materiaRepository.getReferenceByMateriaNome(data.materia().toUpperCase());

        if (!professor.getMateria().getMateriaNome().equalsIgnoreCase(matreria.getMateriaNome()))
            return ResponseEntity.badRequest().body(new ExceptMessage("Professor Indicado nao pode postar pela Materia " + matreria.getMateriaNome() + "!"));
        ConteudoExtra contExtra;
        if (data.tipoConteudo() == TipoConteudo.VIDEO) {
            contExtra = new ConteudoExtra(matreria, data.titulo(), data.tipoConteudo(), null, data.videoUrl(), professor);
        } else {
            contExtra = new ConteudoExtra(matreria, data.titulo(), data.tipoConteudo(), data.descricao(), null, professor);
        }
        conteudoExtraRepository.save(contExtra);

        return ResponseEntity.ok().body(new ResposnseConteudoExtraDTO(contExtra));

    }

    public ResponseEntity removerConteudoExtra(String id){
        if(conteudoExtraRepository.findById(id).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Conteudo Extra com o id informado nao encontrado!"));
        var conteudoDeletado = conteudoExtraRepository.getReferenceById(id);
        conteudoExtraRepository.deleteById(id);
        return ResponseEntity.ok().body(new ResposnseConteudoExtraDTO(conteudoDeletado));
    }

    public ResponseEntity updateConteudoExtra(UpdateConteudoExtraDTO data){
        if(conteudoExtraRepository.findById(data.ConteudoId()).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Conteudo Extra com id: "+data.ConteudoId()+" inexistente!"));
        var conteudoSelect = conteudoExtraRepository.getReferenceById(data.ConteudoId());

        if(!data.titulo().isBlank()){
            conteudoSelect.setTitulo(data.titulo());
        }
        if(data.videoUrl()!=null){
            conteudoSelect.setVideoUrl(data.videoUrl());
        }
        if(data.descricao()!=null){
            conteudoSelect.setDescricao(data.descricao());
        }

        var conteudoUpdated = conteudoExtraRepository.getReferenceById(data.ConteudoId());
        return ResponseEntity.ok().body(new ResposnseConteudoExtraDTO(conteudoUpdated));
    }

    public ResponseEntity showAllContExtra(){
        if(conteudoExtraRepository.findAll().isEmpty())return ResponseEntity.status(204).body(new ExceptMessage("Conteúdos extras nao eoncontrados!"));
        List<ResposnseConteudoExtraDTO> listaConteudoExtra = conteudoExtraRepository.findAll().stream().map(ResposnseConteudoExtraDTO::new).toList();
        return ResponseEntity.ok().body(listaConteudoExtra);
    }

}
