package Educa.plus.Educa.domain.usuario;

public record UsuarioDetalhadoDTO(Long  id, String login, UserRole role) {
    public  UsuarioDetalhadoDTO(Usuario user){
        this(user.getId(), user.getLogin(), user.getRole());
    }
}
