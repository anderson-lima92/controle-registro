package com.lima.api.controleregistro.dominio.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "frequencias")
public class Frequencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long idUsuario;
    private LocalDateTime entrada;
    private LocalDateTime saidaAlmoco;
    private LocalDateTime entradaAlmoco;
    private LocalDateTime saida;
    private long horasTrabalhadas;
    private long horasExtras;

    public void calcularHorasTrabalhadas() {
        if (entrada != null && saida != null) {
            Duration manha = Duration.between(entrada, saidaAlmoco);
            Duration tarde = Duration.between(entradaAlmoco, saida);
            long totalHoras = manha.toHours() + tarde.toHours();
            setHorasTrabalhadas(totalHoras);
            if (totalHoras > 8) {
                setHorasExtras(totalHoras - 8);
            } else {
                setHorasExtras(0);
            }
        }
    }
}
