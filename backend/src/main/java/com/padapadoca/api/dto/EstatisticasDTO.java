package com.padapadoca.api.dto;

public class EstatisticasDTO {

    private long totalAvaliacoes;
    private double mediaGeral;
    private double mediaAtendimento;
    private double mediaProdutos;
    private double mediaAmbiente;
    private double percentualPositivas;
    private String tempoEsperaMaisComum;
    private String comoConheceuMaisComum;

    public EstatisticasDTO(long totalAvaliacoes, double mediaGeral, double mediaAtendimento,
                           double mediaProdutos, double mediaAmbiente, double percentualPositivas,
                           String tempoEsperaMaisComum, String comoConheceuMaisComum) {
        this.totalAvaliacoes = totalAvaliacoes;
        this.mediaGeral = mediaGeral;
        this.mediaAtendimento = mediaAtendimento;
        this.mediaProdutos = mediaProdutos;
        this.mediaAmbiente = mediaAmbiente;
        this.percentualPositivas = percentualPositivas;
        this.tempoEsperaMaisComum = tempoEsperaMaisComum;
        this.comoConheceuMaisComum = comoConheceuMaisComum;
    }

    public long getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public double getMediaGeral() {
        return mediaGeral;
    }

    public double getMediaAtendimento() {
        return mediaAtendimento;
    }

    public double getMediaProdutos() {
        return mediaProdutos;
    }

    public double getMediaAmbiente() {
        return mediaAmbiente;
    }

    public double getPercentualPositivas() {
        return percentualPositivas;
    }

    public String getTempoEsperaMaisComum() {
        return tempoEsperaMaisComum;
    }

    public String getComoConheceuMaisComum() {
        return comoConheceuMaisComum;
    }
}