package Educa.plus.Educa.repositories;

import Educa.plus.Educa.domain.userAnalises.UserEmAnalise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEmAnaliseRepository extends JpaRepository<UserEmAnalise, Long> {


    @Query("SELECT count(*) FROM UserEmAnalise u")
    Integer filaDeUsuarios();

    UserEmAnalise findAllByLogin(String login);
}
