package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.usuario.AtualizaUsuarioDTO;
import Educa.plus.Educa.domain.usuario.CadastroUsuarioDTO;
import Educa.plus.Educa.domain.usuario.DadosAutenticacao;
import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.infra.exception.ExceptMessage;
import Educa.plus.Educa.infra.resposnse.ResponseMessage;
import Educa.plus.Educa.infra.security.DadosTokenJWT;
import Educa.plus.Educa.infra.security.TokenService;
import Educa.plus.Educa.repositories.UserRepository;
import Educa.plus.Educa.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;


@RestController
@RequestMapping("auth")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity efetuarLoginUser(@RequestBody @Valid DadosAutenticacao dadosAutenticacaos) {
        if(userRepository.findAll().isEmpty())return ResponseEntity.badRequest().body(new ExceptMessage("Não Existem Usuarios cadastrados!"));
        if(userRepository.findByLogin(dadosAutenticacaos.login())==null)return ResponseEntity.badRequest().body(new ExceptMessage("Não Existem Usuarios cadastrados com esse nome!"));
        var Authenticationtoken = new UsernamePasswordAuthenticationToken(dadosAutenticacaos.login(),
                dadosAutenticacaos.senha());
        Long userId = userServices.getUserId(dadosAutenticacaos);
        var authentication = manager.authenticate(Authenticationtoken);
        System.out.println(authentication);
        var tokenJWT = tokenService.geradorToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, userId));
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Valid CadastroUsuarioDTO data){
        return userServices.cadastraUsuario(data);
    }

    @PutMapping("update")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity updateUser(@RequestBody @Valid AtualizaUsuarioDTO dados){
        return userServices.AtualizaUsuario(dados);
    }
    @DeleteMapping("delete/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity DeleteUser(@PathVariable @Valid Long  id){
        return userServices.DeltaUsuario(id);
    }

    @GetMapping("/usuarios")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity getAllusers(){
        return userServices.getAllUsers();
    }
    @GetMapping("/usuarios/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity getUsersById(@PathVariable @Valid Long  id){
        return userServices.getUserById(id);
    }

}
