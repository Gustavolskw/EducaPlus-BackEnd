package Educa.plus.Educa.infra.security;

import Educa.plus.Educa.domain.usuario.Usuario;
import Educa.plus.Educa.infra.exception.TratamentoDeErros;
import Educa.plus.Educa.infra.exception.ValidacaoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    final String issuer = "API EducaPlus";
    @Value("${api.security.token.secret}")
    private String secret;

    public String geradorToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withClaim("role", usuario.getRole().ordinal())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar um token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer(issuer)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            throw new RuntimeException("TOKEN INVALIDO OU EXPIRADO! "+exception);
        }
    }

    private Instant dataExpiracao() {
        // metodo de desgaste apos 2 horas da geraçao
        // "-03:00" fuso horario do brasil
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));

    }

}
