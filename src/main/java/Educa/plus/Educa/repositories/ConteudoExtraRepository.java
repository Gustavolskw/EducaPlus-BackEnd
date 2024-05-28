package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.conteudo_extra.ConteudoExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConteudoExtraRepository extends JpaRepository<ConteudoExtra, String> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT materia_id from conteudo_extra")
    List<Long> getAllMatriasidDistinct();
}
