package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<RespostaAtividade, String> {
    List<RespostaAtividade> findAllByAtividade(Atividades atividadeId);

    void deleteByAtividade(Atividades atividade);

    RespostaAtividade findByAlunoAndAtividade(Usuario user, Atividades atividade);
}
