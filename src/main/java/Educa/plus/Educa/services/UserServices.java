package Educa.plus.Educa.services;

import Educa.plus.Educa.domain.usuario.*;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.repositories.MateriaRepository;
import Educa.plus.Educa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public ResponseEntity cadastraUsuario(CadastroUsuarioDTO data){
        if(userRepository.findByLogin(data.login())!=null) return ResponseEntity.badRequest().body(new ExceptMessage("Usuario Já Existente"));
        String materia;
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
    if(data.materia().isEmpty()){
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role(), null );
        this.userRepository.save(newUser);
    }else {
         var materiaCad = materiaRepository.getReferenceByMateriaNome(data.materia());
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role(), materiaCad);
        this.userRepository.save(newUser);
    }
       return ResponseEntity.ok().body(new ResponseMessage("Usuario Cadastrado com sucesso"));
    }

    public ResponseEntity AtualizaUsuario(AtualizaUsuarioDTO dados) {
        var buscaUser = userRepository.econtrePorLogin(dados.login());
       Usuario usuarioAlterado =  userRepository.getReferenceById(buscaUser.getId());
        String encryptedNewPassword = new BCryptPasswordEncoder().encode(dados.newSenha());
        usuarioAlterado.updateUser(dados.login(), encryptedNewPassword);
        return ResponseEntity.ok().body(new ResponseMessage("Usuario:"+ usuarioAlterado.getLogin()+" alterado com Sucesso"));
    }
    public ResponseEntity DeltaUsuario(Long id){
        Usuario user = userRepository.getReferenceById(id);
        if(user == null){
            return ResponseEntity.status(400).body(new ExceptMessage("Usuario nao encontrado"));
        }
        userRepository.deleteById(user.getId());
        return ResponseEntity.ok().body(new ResponseMessage("Usuario Deletado com Sucesso!"));
    }

    public ResponseEntity getAllUsers() {
        List<UsuarioDetalhadoDTO> listUsers = userRepository.findAll().stream().map(UsuarioDetalhadoDTO::new).toList();
        return ResponseEntity.ok().body(listUsers);
    }

    public ResponseEntity getUserById(Long  id) {
       Usuario user = userRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new UsuarioDetalhadoDTO(user));
    }

    public Long getUserId(DadosAutenticacao dadosAutenticacaos) {
        Usuario user = userRepository.getRefereceByLogin(dadosAutenticacaos.login());
        return user.getId();
    }
}
