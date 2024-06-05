package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface RespostaRepository extends JpaRepository<RespostaAtividade, String> {
    List<RespostaAtividade> findAllByAtividade(Atividades atividadeId);

    void deleteByAtividade(Atividades atividade);

    RespostaAtividade findByAlunoAndAtividade(Usuario user, Atividades atividade);
@Query(nativeQuery = true, value ="SELECT atv_ft.id_atividade_feita, atv_ft.atividades_id, atv_ft.resposta, atv_ft.alunos_id FROM atividades_feitas atv_ft " +
        "LEFT JOIN atividade_feita_notas atv_nt " +
        "ON atv_ft.id_atividade_feita = atv_nt.atividade_feita_id " +
        "WHERE atv_nt.nota IS NULL" )
    List<RespostaAtividade> buscarTodasRespostasSemNota();

@Query(nativeQuery = true, value="SELECT atv_ft.id_atividade_feita, atv_ft.atividades_id, atv_ft.alunos_id, atv_ft.resposta FROM atividades_feitas atv_ft \n" +
        "INNER JOIN atividades atv ON atv.id_atividade = atv_ft.atividades_id\n" +
        "INNER JOIN users ON users.id = atv.professores_id\n" +
        "INNER JOIN materias mat ON mat.id_materias = users.materias_id\n" +
        "LEFT JOIN atividade_feita_notas atv_ft_nt ON atv_ft_nt.atividade_feita_id = atv_ft.id_atividade_feita\n" +
        "WHERE atv_ft_nt.nota IS NULL AND mat.id_materias = :materiaId")
    List<RespostaAtividade> buscaAtividadesDoProfessor(@Param("materiaId") Long materiaId);
}
