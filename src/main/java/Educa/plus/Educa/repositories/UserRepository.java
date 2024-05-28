package Educa.plus.Educa.repositories;


import Educa.plus.Educa.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String username);


    @Query("""
            SELECT u FROM Usuario u  WHERE u.login = :login
            """)
    Usuario econtrePorLogin(String login);


    Usuario getRefereceByLogin(String login);
}
