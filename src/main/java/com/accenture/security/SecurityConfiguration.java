package com.accenture.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http

                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/client/**").permitAll()
                        .requestMatchers("/admin/**").permitAll()
                                .requestMatchers("/location/**").permitAll()
//                        .requestMatchers("/admin/creation").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/location/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/location/**").permitAll()
//                        .requestMatchers(HttpMethod.PUT,"/location/**").permitAll()
//                        .requestMatchers(HttpMethod.PATCH,"/location/**").permitAll()
//                                .requestMatchers(HttpMethod.DELETE,"/location/**").permitAll()
//                        .requestMatchers(HttpMethod.DELETE,"/location/**").hasAnyRole("ADMIN","SUPERADMIN") // superadmin est inventé juste pour l'exemple
                        .anyRequest().permitAll()  // attrape toutes les requêtes restantes qui n'ont pas encore été filtrées ou autorisées
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    UserDetailsManager userDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        jdbcUserDetailsManager.setUsersByUsernameQuery("select email, motDePasse, 1 from utilisateurConnecte where email = ?");
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select email, role from utilisateurConnecte where email = ?");
//        return jdbcUserDetailsManager;
//    }
}
