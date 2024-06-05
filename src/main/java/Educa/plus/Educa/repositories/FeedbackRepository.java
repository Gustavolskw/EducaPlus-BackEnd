package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.feedbacks.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findAllByAtividade(Atividades atividade);

    void deleteByAtividade(Atividades atividade);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "\tmat.materia_nome as Materia,\n" +
            "\tcount(feed.experiencia) as FeedBacks,\n" +
            "    SUM(feed.experiencia = \"NEUTRA\") as Neutras, \n" +
            "    SUM(feed.experiencia = \"POSITIVA\") as Positivas, \n" +
            "    SUM(feed.experiencia = \"NEGATIVA\") as Negativas,\n" +
            "    format((sum(feed.experiencia = \"POSITIVA\") / count(feed.experiencia) * 100 ),2) as Porcent_Pos\n" +
            "FROM feedbacks_atividades feed\n" +
            "INNER JOIN atividades atv ON atv.id_atividade = feed.atividade_id\n" +
            "INNER JOIN materias mat ON mat.id_materias = atv.materiax_id\n" +
            "GROUP BY mat.id_materias;")
    List<Object[]> buscaRelatorioDeAtividades();
}
