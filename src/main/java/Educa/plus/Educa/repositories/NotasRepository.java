package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.notas_das_respostas.NotasAtividade;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotasRepository extends JpaRepository<NotasAtividade, Long> {

    @Query("SELECT n FROM NotasAtividade n INNER JOIN RespostaAtividade r ON r.idResposta = n.respostaAtividade.idResposta WHERE r.aluno = :userId")
    List<NotasAtividade> pegarNotasDoAlunoPorId(Usuario userId);

   Optional<NotasAtividade> findAllByRespostaAtividade(RespostaAtividade respostaDoAluno);

    void deleteAllByRespostaAtividade(RespostaAtividade atividadeACH);

    NotasAtividade findByRespostaAtividade(RespostaAtividade atividadeACH);


}
