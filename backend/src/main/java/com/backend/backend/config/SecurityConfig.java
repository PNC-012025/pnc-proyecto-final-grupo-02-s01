package com.backend.backend.config;

import com.backend.backend.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1) Habilita CORS (usa la configuración de WebConfig)
                .cors(Customizer.withDefaults())
                // 2) Deshabilita CSRF (si usas tokens JWT)
                .csrf(csrf -> csrf.disable())
                // 3) Permite todas las peticiones OPTIONS sin autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/**", "/users/register").permitAll()
                        .anyRequest().authenticated()
                )
                // 4) Añade tu filtro JWT
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 5) HTTP Basic (opcional)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}