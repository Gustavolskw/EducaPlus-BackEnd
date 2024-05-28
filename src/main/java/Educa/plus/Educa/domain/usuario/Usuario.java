package Educa.plus.Educa.domain.usuario;


import Educa.plus.Educa.domain.materia.Materia;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "Usuario")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @JoinColumn(name = "materias_id")
    @ManyToOne
    private Materia materia;



    public Usuario(String login, String senha, UserRole role, Materia materia){
        this.login = login;
        this.senha = senha;
        this.role = role;
        this.materia = materia;
    }
    public void updateUser(String login, String novaSenha){
        this.login = login;
        this.senha = novaSenha;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.getRole().equals(UserRole.ADMIN)){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_STAFF"),
                            new SimpleGrantedAuthority("ROLE_USER"));
        }
        else if(this.getRole().equals(UserRole.STAFF)){
            return List.of(new SimpleGrantedAuthority("ROLE_STAFF"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }



    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
