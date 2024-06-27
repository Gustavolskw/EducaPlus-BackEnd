package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AtividadesRepository extends JpaRepository<Atividades, String> {
    List<Atividades>  findAllByMateria(Materia materia);
    @Query(nativeQuery = true, value = "SELECT DISTINCT materiax_id FROM atividades")
    List<Long> getAllMatriasidDistinct();

@Query("SELECT DISTINCT a.data from Atividades a ORDER BY day(a.data)")
    List<LocalDate>buscaDataOrdenandoPorDia();

@Query(nativeQuery = true, value = "select atv.id_atividade from atividades atv\n" +
        "INNER JOIN atividades_feitas af ON af.atividades_id = atv.id_atividade \n" +
        "INNER JOIN users usr ON usr.id = af.alunos_id\n" +
        "where usr.id = :userId\n")
    List<String> listaDeAtividadesQueUsuarioFez(@Param("userId") Long userId);
}
