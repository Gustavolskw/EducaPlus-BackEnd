package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.feedbacks.EnviarFeedbackDTO;
import Educa.plus.Educa.domain.feedbacks.Feedback;
import Educa.plus.Educa.domain.feedbacks.RespostaFeedbackDTO;
import Educa.plus.Educa.domain.feedbacks.UpdateFeedbackDTO;
import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.repositories.AtividadesRepository;
import Educa.plus.Educa.repositories.FeedbackRepository;
import Educa.plus.Educa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServices {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtividadesRepository atividadesRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;


    public ResponseEntity showAllFromOneAtividade(String id){
        if(feedbackRepository.findAll().isEmpty())return ResponseEntity.status(404).body("Nao Existem Feedbacks Postados");
        if(atividadesRepository.findById(id).isEmpty())return ResponseEntity.status(400).body("Não Existem Atividades postadas com o ID informado !");
        Atividades atividade = atividadesRepository.getReferenceById(id);
        List<RespostaFeedbackDTO> listaFeedBacksDaAtividade = feedbackRepository.findAllByAtividade(atividade).stream().map(RespostaFeedbackDTO::new).toList();
        if(listaFeedBacksDaAtividade.isEmpty()){
            return ResponseEntity.status(204).body("Nao Existem Feedbacks Postados para essa atividade!");
        }
        return ResponseEntity.status(200).body(listaFeedBacksDaAtividade);
    }

    public ResponseEntity postAFeedback(EnviarFeedbackDTO data){
        if(atividadesRepository.findById(data.atividadeid()).isEmpty())return ResponseEntity.status(204).body(new ExceptMessage("Atividade com o ID informado nao encontrada!"));
        if(userRepository.findById(data.userId()).isEmpty())return ResponseEntity.status(204).body(new ExceptMessage("Usuario com o ID informado nao encontrado!"));

        Usuario user = userRepository.getReferenceById(data.userId());
        Atividades atividade = atividadesRepository.getReferenceById(data.atividadeid());

        Feedback feedbackNovo = new Feedback(atividade, user, data);

        feedbackRepository.save(feedbackNovo);


        return ResponseEntity.status(201).body(new ResponseMessage("Feedback Adicionado com sucesso, Obrigado por contribuir com sua experiencia"));
    }

    public ResponseEntity removeFeedback(String id){
       if(feedbackRepository.findById(id).isEmpty()) return ResponseEntity.status(203).body(new ExceptMessage("Feed"));
       feedbackRepository.deleteById(id);
       return ResponseEntity.ok().body(new ResponseMessage("Mensagem Deletada Com sucesso!"));
    }

    public ResponseEntity updateFeedback(String id, UpdateFeedbackDTO data){
        if(feedbackRepository.findById(id).isEmpty())return ResponseEntity.status(203).body(new ResponseMessage("Feedback com ID informado nao encontrado!"));
        var feedbackToUpdate = feedbackRepository.getReferenceById(id);
        if(data.experiencia()!=null){
            feedbackToUpdate.setExperiencia(data.experiencia());
        }
        if(!data.opiniao().isBlank()){
            feedbackToUpdate.setOpiniao(data.opiniao());
        }
        var feedbackUPDT = feedbackRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new RespostaFeedbackDTO(feedbackUPDT));
    }




}
