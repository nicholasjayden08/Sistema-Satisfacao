package com.padapadoca.api.dto;

public class EstatisticasDTO {

    private long totalAvaliacoes;
    private double mediaGeral;
    private double mediaAtendimento;
    private double mediaProdutos;
    private double mediaAmbiente;
    private double percentualPositivas; // % de avaliações com média >= 4

    public EstatisticasDTO(long totalAvaliacoes, double mediaGeral, double mediaAtendimento,
                            double mediaProdutos, double mediaAmbiente, double percentualPositivas) {
        this.totalAvaliacoes = totalAvaliacoes;
        this.mediaGeral = mediaGeral;
        this.mediaAtendimento = mediaAtendimento;
        this.mediaProdutos = mediaProdutos;
        this.mediaAmbiente = mediaAmbiente;
        this.percentualPositivas = percentualPositivas;
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

    public double getPercentualPositivas() {
        return percentualPositivas;
    }
}
