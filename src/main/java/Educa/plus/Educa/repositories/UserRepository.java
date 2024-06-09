package Educa.plus.Educa.repositories;


import Educa.plus.Educa.domain.usuario.RelatorioPostProfessores;
import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
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


@Query(nativeQuery = true, value="SELECT \n" +
        "    users.login, \n" +
        "    mat.materia_nome AS Materia,\n" +
        "    FORMAT(AVG(notas.nota), 2) AS media,\n" +
        "    MAX(notas.nota) AS Maior_nota,\n" +
        "    MIN(notas.nota) AS Menor_nota,\n" +
        "    FORMAT(100.0 * SUM(CASE WHEN notas.nota >= 7 THEN 1 ELSE 0 END) / COUNT(notas.nota), 2) AS Porcnt_boa\n" +
        "FROM \n" +
        "    users \n" +
        "INNER JOIN \n" +
        "    atividades_feitas feitas ON feitas.alunos_id = users.id\n" +
        "INNER JOIN \n" +
        "    atividade_feita_notas notas ON feitas.id_atividade_feita = notas.atividade_feita_id\n" +
        "INNER JOIN \n" +
        "    atividades atv ON atv.id_atividade = feitas.atividades_id\n" +
        "INNER JOIN \n" +
        "    materias mat ON mat.id_materias = atv.materiax_id\n" +
        "WHERE \n" +
        "\tDAY(atv.data) BETWEEN :diaInit AND :diaFinal\n" +
        "\tAND MONTH(atv.data) BETWEEN :mesInit AND  :mesFinal\n" +
        "    AND YEAR(atv.data) = :ano\n" +
        "GROUP BY \n" +
        "    users.login, mat.materia_nome \n" +
        "ORDER BY \n" +
        "    users.login ASC;")
    List<Object[]> relatorioNotasDosAlunos(@Param("diaInit")int diaInit, @Param("diaFinal")int diaFinal,@Param("mesInit") int mesInit,@Param("mesFinal") int mesFinal ,  @Param("ano")int ano);

@Query(nativeQuery = true, value = "SELECT \n" +
        "    u.login AS PROFESSOR,\n" +
        "    COUNT(atv.id_atividade) AS QTD_ATIVIDADES,\n" +
        "    mat.materia_nome AS MATERIA,\n" +
        "    FORMAT((COUNT(atv.id_atividade) * 100.0 / total_atividades.total), 2) AS PORCENTAGEM,\n" +
        "    total_atividades.total AS ATIVIDADES_TOTAIS\n" +
        "FROM \n" +
        "    users u \n" +
        "INNER JOIN \n" +
        "    atividades atv ON atv.professores_id = u.id\n" +
        "INNER JOIN \n" +
        "    materias mat ON atv.materiax_id = mat.id_materias\n" +
        "CROSS JOIN \n" +
        "    (SELECT COUNT(*) AS total FROM atividades WHERE DAY(data) BETWEEN :diaInit AND :diaFinal AND MONTH(data) BETWEEN :mesInit AND  :mesFinal AND YEAR(data) = :ano) AS total_atividades\n" +
        "WHERE \n" +
        "    DAY(atv.data) BETWEEN :diaInit AND :diaFinal AND MONTH(atv.data) BETWEEN :mesInit AND  :mesFinal AND YEAR(atv.data) = :ano\n" +
        "GROUP BY \n" +
        "    u.login, mat.materia_nome, total_atividades.total\n" +
        "ORDER BY \n" +
        "    COUNT(atv.id_atividade) DESC;")
    List<Object[]> relatorioDePostagens(@Param("diaInit")int diaInit, @Param("diaFinal")int diaFinal, @Param("mesInit") int mesInit, @Param("mesFinal") int mesFinal , @Param("ano")int ano);





}
