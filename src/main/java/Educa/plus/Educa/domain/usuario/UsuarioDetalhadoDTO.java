package Educa.plus.Educa.domain.usuario;

public record UsuarioDetalhadoDTO(Long  id, String login, UserRole role, String materia, Boolean disponibilidade) {
    public  UsuarioDetalhadoDTO(Usuario user){
        this(user.getId(), user.getLogin(), user.getRole(), user.getMateria() != null ? user.getMateria().getMateriaNome() : null, user.getAtivo() );
    }
}
