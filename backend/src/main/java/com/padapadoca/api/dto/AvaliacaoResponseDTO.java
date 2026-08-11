package com.padapadoca.api.dto;

import com.padapadoca.api.model.Avaliacao;

import java.time.LocalDateTime;

public class AvaliacaoResponseDTO {

    private Long id;
    private String nomeCliente;
    private Integer notaGeral;
    private Integer notaAtendimento;
    private Integer notaProdutos;
    private Integer notaAmbiente;
    private String tempoEspera;
    private String comoConheceu;
    private String comentario;
    private LocalDateTime dataHora;
    private double media;
    private boolean recomendaGoogle;

    public static AvaliacaoResponseDTO fromEntity(Avaliacao a) {
        AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO();
        dto.id = a.getId();
        dto.nomeCliente = a.getNomeCliente();
        dto.notaGeral = a.getNotaGeral();
        dto.notaAtendimento = a.getNotaAtendimento();
        dto.notaProdutos = a.getNotaProdutos();
        dto.notaAmbiente = a.getNotaAmbiente();
        dto.tempoEspera = a.getTempoEspera();
        dto.comoConheceu = a.getComoConheceu();
        dto.comentario = a.getComentario();
        dto.dataHora = a.getDataHora();
        dto.media = Math.round(a.calcularMedia() * 10) / 10.0;
        dto.recomendaGoogle = a.calcularMedia() >= 4.0;
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Integer getNotaGeral() {
        return notaGeral;
    }

    public Integer getNotaAtendimento() {
        return notaAtendimento;
    }

    public Integer getNotaProdutos() {
        return notaProdutos;
    }

    public Integer getNotaAmbiente() {
        return notaAmbiente;
    }

    public String getTempoEspera() {
        return tempoEspera;
    }

    public String getComoConheceu() {
        return comoConheceu;
    }

    public String getComentario() {
        return comentario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public double getMedia() {
        return media;
    }

    public boolean isRecomendaGoogle() {
        return recomendaGoogle;
    }
}
