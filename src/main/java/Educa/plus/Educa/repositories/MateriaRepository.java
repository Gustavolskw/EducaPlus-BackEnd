package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.materia.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    Materia getReferenceByMateriaNome(String materia);


    Materia findByMateriaNome(String materia);

    List<Materia> findAllByMateriaNomeLike(String materia);

    Materia getReferenceByMateriaNomeLike(String materia);

    Materia findByMateriaNomeLike(String upperCase);
    @Query(nativeQuery = true, value = "SELECT DISTINCT id_materias, materia_nome FROM materias mat \n" +
        "INNER JOIN atividades atv ON atv.materiax_id = mat.id_materias\n" +
        "INNER JOIN atividades_feitas atv_ft ON atv_ft.atividades_id = atv.id_atividade\n" +
        "INNER JOIN atividade_feita_notas atv_ft_nt ON atv_ft_nt.atividade_feita_id = atv_ft.id_atividade_feita")
    List<Materia> buscarMateriasQueTenhamNotas();
}
