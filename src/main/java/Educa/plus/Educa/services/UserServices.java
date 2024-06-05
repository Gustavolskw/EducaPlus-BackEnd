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
        if(!usuarioAlterado.getAtivo())return ResponseEntity.badRequest().body(new ExceptMessage("Usuario inativo!"));
        String encryptedNewPassword = new BCryptPasswordEncoder().encode(dados.newSenha());
        usuarioAlterado.updateUser(dados.login(), encryptedNewPassword);
        return ResponseEntity.ok().body(new ResponseMessage("Usuario:"+ usuarioAlterado.getLogin()+" alterado com Sucesso"));
    }



    public ResponseEntity DeltaUsuario(Long id){
        if(userRepository.findById(id).isEmpty()) return ResponseEntity.status(404).body(new ExceptMessage("Usuario nao encontrado"));
        Usuario user = userRepository.getReferenceById(id);
        user.desativaUser();
        return ResponseEntity.ok().body(new ResponseMessage("Usuario Inativado com Sucesso!"));
    }

    public ResponseEntity reativaUser(Long id){
        if(userRepository.findById(id).isEmpty()) return ResponseEntity.status(404).body(new ExceptMessage("Usuario nao encontrado"));
        try{
            Usuario user = userRepository.getReferenceById(id);
            user.setAtivo(true);
        }catch (Exception e){
            return ResponseEntity.ok().body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok().body(new ResponseMessage("Usuario Reativado com Sucesso!"));

    }

    public ResponseEntity getAllUsers() {
        if(userRepository.findAll().isEmpty()) return ResponseEntity.status(404).body(new ExceptMessage("Usuario nao encontrado"));
        List<UsuarioDetalhadoDTO> listUsers = userRepository.findAll().stream().map(UsuarioDetalhadoDTO::new).toList();
        return ResponseEntity.ok().body(listUsers);
    }

    public ResponseEntity getUserById(Long  id) {
        if(userRepository.findById(id).isEmpty()) return ResponseEntity.status(404).body(new ExceptMessage("Usuario nao encontrado"));
       Usuario user = userRepository.getReferenceById(id);
        if(!user.getAtivo())return ResponseEntity.badRequest().body(new ExceptMessage("Usuario inativo!"));
        return ResponseEntity.ok().body(new UsuarioDetalhadoDTO(user));
    }

    public Long getUserId(DadosAutenticacao dadosAutenticacaos) {
        Usuario user = userRepository.getRefereceByLogin(dadosAutenticacaos.login());
        return user.getId();
    }
/*
    public ResponseEntity getAllUsersAtivos() {
        if(userRepository.findAll().isEmpty()) return ResponseEntity.status(404).body(new ExceptMessage("Usuario nao encontrado"));
        if(userRepository.findAllByAtivoTrue().isEmpty())return ResponseEntity.status(404).body(new ExceptMessage("Sem Usuarios Ativos"));
        List<UsuarioDetalhadoDTO> usuariosAtivosList = userRepository.findAllByAtivoTrue().stream().map(UsuarioDetalhadoDTO::new).toList();
        return ResponseEntity.ok().body(usuariosAtivosList);
    }

    public ResponseEntity getAllUsersInativos() {
        if(userRepository.findAll().isEmpty()) return ResponseEntity.status(404).body(new ExceptMessage("Usuario nao encontrado"));
        if(userRepository.findAllByAtivoFalse().isEmpty())return ResponseEntity.status(404).body(new ExceptMessage("Sem Usuarios Inativos"));
        List<UsuarioDetalhadoDTO> usuariosInativosList = userRepository.findAllByAtivoFalse().stream().map(UsuarioDetalhadoDTO::new).toList();
        return ResponseEntity.ok().body(usuariosInativosList);
    }
    */
 
}
