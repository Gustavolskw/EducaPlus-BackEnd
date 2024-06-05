package Educa.plus.Educa.repositories;


import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String username);


    @Query("""
            SELECT u FROM Usuario u  WHERE u.login = :login
            """)
    Usuario econtrePorLogin(String login);


    Usuario getRefereceByLogin(String login);

    List<Usuario> findAllByAtivoTrue();

    List<Usuario>findAllByAtivoFalse();

    @Query(nativeQuery = true, value = "SELECT ativo FROM users u WHERE login LIKE :userLogin")
    Integer  buscaDisponibilidadeDoUsuario(@Param("userLogin") String userLogin);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "    users.login, \n" +
            "    COUNT(atv_ft.id_atividade_feita) AS Atv_feitas,\n" +
            "    mat.materia_nome AS Materia,\n" +
            "    total_atividades.total AS Total_Atividades,\n" +
            "    format((COUNT(atv_ft.id_atividade_feita) / total_atividades.total * 100),2) AS Percent_Atv_Feitas\n" +
            "FROM \n" +
            "    users\n" +
            "INNER JOIN \n" +
            "    atividades_feitas atv_ft ON atv_ft.alunos_id = users.id\n" +
            "INNER JOIN \n" +
            "    atividades atv ON atv.id_atividade = atv_ft.atividades_id\n" +
            "INNER JOIN \n" +
            "    materias mat ON mat.id_materias = atv.materiax_id\n" +
            "INNER JOIN (\n" +
            "    SELECT \n" +
            "        mat.id_materias,\n" +
            "        COUNT(atv.id_atividade) AS total\n" +
            "    FROM \n" +
            "        atividades atv\n" +
            "    INNER JOIN \n" +
            "        materias mat ON mat.id_materias = atv.materiax_id\n" +
            "    GROUP BY \n" +
            "        mat.id_materias\n" +
            ") total_atividades ON mat.id_materias = total_atividades.id_materias\n" +
            "WHERE \n" +
            "    users.role = 'USER'\n" +
            "GROUP BY \n" +
            "    users.login, mat.materia_nome, total_atividades.total;")
    List<Object[]> listaDeParticipacao();
}
