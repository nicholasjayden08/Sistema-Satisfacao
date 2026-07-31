package com.padapadoca.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Liberado geral por enquanto pra facilitar o desenvolvimento local.
    // Antes de publicar pra padaria de verdade, trocar "*" pelo domínio
    // real onde a página vai ficar hospedada.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // "Authorization" precisa estar liberado aqui, senao o preflight do
                // navegador bloqueia o Basic Auth que o painel admin passa a enviar.
                .allowedHeaders("*");
    }
}
