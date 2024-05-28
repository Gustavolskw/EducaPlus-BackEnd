package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AtividadesRepository extends JpaRepository<Atividades, String> {
    List<Atividades>  findAllByMateria(Materia materia);
    @Query(nativeQuery = true, value = "SELECT DISTINCT materiax_id FROM atividades")
    List<Long> getAllMatriasidDistinct();
}
