package com.padapadoca.api.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Protege o painel administrativo com login basico (usuario/senha).
 *
 * - POST /api/avaliacoes continua PUBLICO: e o endpoint que o cliente usa
 *   na pagina do QR code, sem login.
 * - GET  /api/avaliacoes e /api/avaliacoes/estatisticas passam a exigir
 *   autenticacao: sao os dados que alimentam o painel do dono da padaria.
 * - Console do H2 continua livre so em desenvolvimento (ver README).
 *
 * Usuario e senha do admin sao configurados em application.properties
 * (padapadoca.admin.username / padapadoca.admin.password) ou por variavel
 * de ambiente. TROQUE a senha padrao antes de publicar em producao.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${padapadoca.admin.username}")
    private String adminUsername;

    @Value("${padapadoca.admin.password}")
    private String adminPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUsername)
                        .password(encoder.encode(adminPassword))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // API sem sessao/cookies: cada chamada do painel manda Basic Auth de novo.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CSRF nao se aplica aqui: nao usamos cookies de sessao, so Basic Auth via header.
                .cors(cors -> {})
.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/avaliacoes").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {})
                // Necessario pro H2 console (usa <frame>) e por ser so uma ferramenta de dev.
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "https://nicholasjayden08.github.io"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
