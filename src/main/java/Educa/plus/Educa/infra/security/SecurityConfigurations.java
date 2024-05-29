package Educa.plus.Educa.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "newUser/register").permitAll();
                    req.requestMatchers(HttpMethod.POST, "auth/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "auth/register").permitAll();
                    req.requestMatchers( "/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/materia/add").hasRole("ADMIN");
                    //req.requestMatchers(HttpMethod.GET, "newUser/analise/list").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "newUser/analise/aprove/{id}").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "newUser/analise/remove/{id}").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.GET, "materia/all").permitAll();
                    req.requestMatchers(HttpMethod.GET, "teste/token").authenticated();
                    //req.requestMatchers(HttpMethod.POST, "conteudo/extra/add").hasRole("STAFF");
                    req.requestMatchers(HttpMethod.DELETE, "conteudo/extra/remove/{id}").hasRole("STAFF");
                    req.requestMatchers(HttpMethod.PUT, "conteudo/extra/update").hasRole("STAFF");
                    //req.requestMatchers(HttpMethod.POST, "atividade/add").hasRole("STAFF");
//                    req.requestMatchers(HttpMethod.DELETE, "atividade/remove/{id}").hasRole("STAFF");
                    req.requestMatchers(HttpMethod.PUT, "atividade/update").hasRole("STAFF");

                    req.anyRequest().permitAll();

                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

