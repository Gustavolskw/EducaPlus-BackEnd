package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.atividades.RespostaAtividadeDTO;
import Educa.plus.Educa.domain.feedbacks.Feedback;
import Educa.plus.Educa.domain.feedbacks.RespostaFeedbackDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findAllByAtividade(Atividades atividade);

    void deleteByAtividade(Atividades atividade);
}
