package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.materia.CadastroMateriaDTO;
import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.userAnalises.CadastroUsuarioEmAnaliseDTO;
import Educa.plus.Educa.domain.userAnalises.ResponseUserEmAnaliseDTO;
import Educa.plus.Educa.domain.userAnalises.TipoUsuario;
import Educa.plus.Educa.domain.userAnalises.UserEmAnalise;
import Educa.plus.Educa.domain.usuario.UserRole;
import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.domain.usuario.UsuarioDetalhadoDTO;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.repositories.MateriaRepository;
import Educa.plus.Educa.repositories.UserEmAnaliseRepository;
import Educa.plus.Educa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEmAnaliseServices {

    @Autowired
    private UserEmAnaliseRepository userEmAnaliseRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MateriaRepository materiaRepository;


    public ResponseEntity createUserToAnalise(CadastroUsuarioEmAnaliseDTO data ){
        if(userEmAnaliseRepository.findAllByLogin(data.login())!=null)return ResponseEntity.badRequest().body(new ExceptMessage("Login ja em uso para usuario em Analise Procure outro nome de acesso!"));
        if(userRepository.econtrePorLogin(data.login())!=null)return ResponseEntity.badRequest().body(new ExceptMessage("Login Ja em uso Procure outro nome de acesso!"));
        String tipoDeUser = String.valueOf(data.tipoUsuario());
        if(tipoDeUser.equalsIgnoreCase("professor")) {
            if (tipoDeUser.equalsIgnoreCase("professor") && data.materia() == null) {
                return ResponseEntity.badRequest().body(new ExceptMessage("Deve ser informado uma materia se o Usuario for um professor!"));
            }
            if (materiaRepository.findByMateriaNome(data.materia().toUpperCase()) == null)
                return ResponseEntity.badRequest().body(new ExceptMessage("Materia informada não encontrada!"));
            Materia materia = materiaRepository.getReferenceByMateriaNome(data.materia().toUpperCase());
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
            var newUser = new UserEmAnalise(data.login(), encryptedPassword, data.motivo(), data.tipoUsuario(), materia);
            userEmAnaliseRepository.save(newUser);
        } else if (tipoDeUser.equalsIgnoreCase("aluno")) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
            var newUser = new UserEmAnalise(data.login(), encryptedPassword, data.motivo(), data.tipoUsuario(), null);
            userEmAnaliseRepository.save(newUser);
        }
        Integer posFila = userEmAnaliseRepository.filaDeUsuarios();
        return ResponseEntity.ok().body(new ResponseMessage("Usuario Inserido na fila de Analise! sua posição na fila é: "+posFila+"º"));
    }

    public ResponseEntity showAllAnalisesUsers(){
        if (userEmAnaliseRepository.findAll().isEmpty())return ResponseEntity.status(404).body(new ExceptMessage("Não Existem usuarios Em analise!"));
        List<ResponseUserEmAnaliseDTO> listaAnalise = userEmAnaliseRepository.findAll().stream().map(ResponseUserEmAnaliseDTO::new).toList();
        return ResponseEntity.ok().body(listaAnalise);
    }

    public ResponseEntity aprovaUserEmAnalise(Long id){
        if(userEmAnaliseRepository.findById(id).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Usuario de "+id+" é inexistente"));
        var usuario = userEmAnaliseRepository.getReferenceById(id);
        String tipoDeUser = String.valueOf(usuario.getTipoUsuario());
        UserRole role;
        if(tipoDeUser.equalsIgnoreCase("professor")){
            if(materiaRepository.findAllByMateriaNomeLike(usuario.getMateria().getMateriaNome()).isEmpty()){
                return ResponseEntity.badRequest().body(new ExceptMessage("Materia: "+usuario.getMateria().getMateriaNome())+" é inexistente");
            }
            var materiaBusc = materiaRepository.getReferenceByMateriaNomeLike(usuario.getMateria().getMateriaNome());
            role = UserRole.STAFF;
            var userAprovado = new Usuario(usuario.getLogin(), usuario.getSenha(), role, materiaBusc);
            userRepository.save(userAprovado);
            userEmAnaliseRepository.deleteById(id);
            return ResponseEntity.ok().body(new UsuarioDetalhadoDTO(userAprovado));
        }else{
            role = UserRole.USER;
            var userAprovado = new Usuario(usuario.getLogin(), usuario.getSenha(), role, null);
            userRepository.save(userAprovado);
            userEmAnaliseRepository.deleteById(id);
        return ResponseEntity.ok().body(new UsuarioDetalhadoDTO(userAprovado));
        }

    }

    public ResponseEntity desaprovaUsuario(Long id) {
        if(userEmAnaliseRepository.findById(id).isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Usuario de "+id+" é inexistente"));
        var userRemvido = userEmAnaliseRepository.getReferenceById(id);
        userEmAnaliseRepository.deleteById(id);
        return ResponseEntity.ok().body(new ResponseMessage("Usuario: "+ userRemvido.getLogin()+" Removido da lista de analise com sucesso"));
    }
}
