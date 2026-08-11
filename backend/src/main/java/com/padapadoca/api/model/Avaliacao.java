package com.padapadoca.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nota geral e sempre obrigatoria (1 a 5). As demais sao opcionais,
    // caso o cliente responda so a pergunta principal.
    @Column(nullable = false)
    private Integer notaGeral;

    private Integer notaAtendimento;

    private Integer notaProdutos;

    private Integer notaAmbiente;

    private String tempoEspera;

    private String comoConheceu;

    @Column(length = 1000)
    private String comentario;

    private String nomeCliente;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    public Avaliacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNotaGeral() {
        return notaGeral;
    }

    public void setNotaGeral(Integer notaGeral) {
        this.notaGeral = notaGeral;
    }

    public Integer getNotaAtendimento() {
        return notaAtendimento;
    }

    public void setNotaAtendimento(Integer notaAtendimento) {
        this.notaAtendimento = notaAtendimento;
    }

    public Integer getNotaProdutos() {
        return notaProdutos;
    }

    public void setNotaProdutos(Integer notaProdutos) {
        this.notaProdutos = notaProdutos;
    }

    public Integer getNotaAmbiente() {
        return notaAmbiente;
    }

    public void setNotaAmbiente(Integer notaAmbiente) {
        this.notaAmbiente = notaAmbiente;
    }

    public String getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(String tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public String getComoConheceu() {
        return comoConheceu;
    }

    public void setComoConheceu(String comoConheceu) {
        this.comoConheceu = comoConheceu;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    // Media entre a nota geral e as categorias que o cliente respondeu.
    // Usada pra decidir se mostramos o CTA do Google (media >= 4) ou nao.
    public double calcularMedia() {
        int soma = notaGeral;
        int quantidade = 1;

        if (notaAtendimento != null) {
            soma += notaAtendimento;
            quantidade++;
        }
        if (notaProdutos != null) {
            soma += notaProdutos;
            quantidade++;
        }
        if (notaAmbiente != null) {
            soma += notaAmbiente;
            quantidade++;
        }

        return (double) soma / quantidade;
    }
}
