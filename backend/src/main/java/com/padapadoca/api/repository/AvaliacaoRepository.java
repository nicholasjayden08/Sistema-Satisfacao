package com.padapadoca.api.repository;

import com.padapadoca.api.model.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Page<Avaliacao> findByDataHoraBetweenOrderByDataHoraDesc(
            LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    List<Avaliacao> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT AVG(a.notaGeral) FROM Avaliacao a WHERE a.dataHora BETWEEN :inicio AND :fim")
    Double mediaNotaGeral(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT AVG(a.notaAtendimento) FROM Avaliacao a WHERE a.notaAtendimento IS NOT NULL AND a.dataHora BETWEEN :inicio AND :fim")
    Double mediaAtendimento(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT AVG(a.notaProdutos) FROM Avaliacao a WHERE a.notaProdutos IS NOT NULL AND a.dataHora BETWEEN :inicio AND :fim")
    Double mediaProdutos(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT AVG(a.notaAmbiente) FROM Avaliacao a WHERE a.notaAmbiente IS NOT NULL AND a.dataHora BETWEEN :inicio AND :fim")
    Double mediaAmbiente(LocalDateTime inicio, LocalDateTime fim);

    long countByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    long countByNotaGeralGreaterThanEqualAndDataHoraBetween(Integer nota, LocalDateTime inicio, LocalDateTime fim);
}
