package com.padapadoca.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvaliacaoRequestDTO {

    @NotNull(message = "A nota geral é obrigatória")
    @Min(value = 1, message = "A nota geral deve ser entre 1 e 5")
    @Max(value = 5, message = "A nota geral deve ser entre 1 e 5")
    private Integer notaGeral;

    @Min(1) @Max(5)
    private Integer notaAtendimento;

    @Min(1) @Max(5)
    private Integer notaProdutos;

    @Min(1) @Max(5)
    private Integer notaAmbiente;

    @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres")
    private String comentario;

    @Size(max = 120)
    private String nomeCliente;

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
}
