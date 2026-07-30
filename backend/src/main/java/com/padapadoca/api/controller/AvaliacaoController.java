package com.padapadoca.api.controller;

import com.padapadoca.api.dto.AvaliacaoRequestDTO;
import com.padapadoca.api.dto.AvaliacaoResponseDTO;
import com.padapadoca.api.dto.EstatisticasDTO;
import com.padapadoca.api.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    // Endpoint PÚBLICO: chamado pela página que o cliente abre no QR code.
    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criar(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO salva = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // Os dois endpoints abaixo alimentam o painel administrativo.
    // IMPORTANTE: ainda não têm autenticação — é o próximo passo antes de ir pra produção
    // (ver README.md, seção "Segurança").

    @GetMapping
    public Page<AvaliacaoResponseDTO> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable) {

        LocalDateTime dataInicio = inicio != null ? inicio : LocalDateTime.now().minusYears(10);
        LocalDateTime dataFim = fim != null ? fim : LocalDateTime.now();

        return service.listar(dataInicio, dataFim, pageable);
    }

    @GetMapping("/estatisticas")
    public EstatisticasDTO estatisticas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        LocalDateTime dataInicio = inicio != null ? inicio : LocalDateTime.now().minusDays(30);
        LocalDateTime dataFim = fim != null ? fim : LocalDateTime.now();

        return service.calcularEstatisticas(dataInicio, dataFim);
    }
}
