package com.padapadoca.api.service;

import com.padapadoca.api.dto.AvaliacaoRequestDTO;
import com.padapadoca.api.dto.AvaliacaoResponseDTO;
import com.padapadoca.api.dto.EstatisticasDTO;
import com.padapadoca.api.model.Avaliacao;
import com.padapadoca.api.repository.AvaliacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository repository;

    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public AvaliacaoResponseDTO salvar(AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNotaGeral(dto.getNotaGeral());
        avaliacao.setNotaAtendimento(dto.getNotaAtendimento());
        avaliacao.setNotaProdutos(dto.getNotaProdutos());
        avaliacao.setNotaAmbiente(dto.getNotaAmbiente());
        avaliacao.setTempoEspera(dto.getTempoEspera());
        avaliacao.setComoConheceu(dto.getComoConheceu());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setNomeCliente(dto.getNomeCliente());
        avaliacao.setDataHora(LocalDateTime.now());

        Avaliacao salva = repository.save(avaliacao);
        return AvaliacaoResponseDTO.fromEntity(salva);
    }

    public Page<AvaliacaoResponseDTO> listar(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return repository.findByDataHoraBetweenOrderByDataHoraDesc(inicio, fim, pageable)
                .map(AvaliacaoResponseDTO::fromEntity);
    }

    public EstatisticasDTO calcularEstatisticas(LocalDateTime inicio, LocalDateTime fim) {
        long total = repository.countByDataHoraBetween(inicio, fim);

        double mediaGeral = arredondar(repository.mediaNotaGeral(inicio, fim));
        double mediaAtendimento = arredondar(repository.mediaAtendimento(inicio, fim));
        double mediaProdutos = arredondar(repository.mediaProdutos(inicio, fim));
        double mediaAmbiente = arredondar(repository.mediaAmbiente(inicio, fim));

        long positivas = repository.countByNotaGeralGreaterThanEqualAndDataHoraBetween(4, inicio, fim);
        double percentualPositivas = total == 0 ? 0 : arredondar((positivas * 100.0) / total);

        return new EstatisticasDTO(total, mediaGeral, mediaAtendimento, mediaProdutos, mediaAmbiente, percentualPositivas);
    }

    private double arredondar(Double valor) {
        if (valor == null) return 0.0;
        return Math.round(valor * 10) / 10.0;
    }
}
