package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.notas_das_respostas.NotasAtividade;
import Educa.plus.Educa.domain.notas_das_respostas.RespostaNotaDTO;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotasRepository extends JpaRepository<NotasAtividade, Long> {

    @Query("SELECT n FROM NotasAtividade n INNER JOIN RespostaAtividade r ON r.idResposta = n.respostaAtividade.idResposta WHERE r.aluno = :userId")
    List<NotasAtividade> pegarNotasDoAlunoPorId(Usuario userId);

   Optional<NotasAtividade> findAllByRespostaAtividade(RespostaAtividade respostaDoAluno);

    void deleteAllByRespostaAtividade(RespostaAtividade atividadeACH);

    NotasAtividade findByRespostaAtividade(RespostaAtividade atividadeACH);

@Query(nativeQuery = true, value = "SELECT notas.atividade_feita_id, notas.id_atividade_feita_notas, notas.nota, notas.avaliador FROM atividade_feita_notas notas \n" +
        "INNER JOIN users ON users.id = notas.avaliador \n" +
        "INNER JOIN materias mat ON mat.id_materias = users.materias_id\n" +
        "WHERE mat.materia_nome LIKE :materiaDoProfessor")
    List<NotasAtividade> encontreNotasPorMateria(@Param("materiaDoProfessor") String materiaDoProfessor);
}
