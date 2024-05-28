package Educa.plus.Educa.domain.userAnalises;

public record ResponseUserEmAnaliseDTO(Long id, String login, String motivo, TipoUsuario tipoUsuario, String materia) {
   public ResponseUserEmAnaliseDTO(UserEmAnalise userEmAnalise) {
           this(userEmAnalise.getId(), userEmAnalise.getLogin(), userEmAnalise.getMotivo(), userEmAnalise.getTipoUsuario(), userEmAnalise.getMateria() != null ? userEmAnalise.getMateria().getMateriaNome() : null);
   }
}
