package com.lima.api.controleregistro.adaptador.repositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lima.api.controleregistro.dominio.modelo.Frequencia;

public interface FrequenciaJpaRepositorio extends JpaRepository<Frequencia, Long> {
    List<Frequencia> findByEntradaBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
    Optional<Frequencia> findByIdUsuarioAndEntradaBetween(Long idUsuario, LocalDateTime startOfDay, LocalDateTime endOfDay);
    Optional<Frequencia> findByIdUsuario(Long idUsuario);
}
