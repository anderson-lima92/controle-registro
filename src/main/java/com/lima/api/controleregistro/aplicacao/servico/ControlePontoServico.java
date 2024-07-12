package com.lima.api.controleregistro.aplicacao.servico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lima.api.controleregistro.aplicacao.porta.FrequenciaRepositorioPorta;
import com.lima.api.controleregistro.dominio.modelo.Frequencia;
import com.lima.api.controleregistro.exception.DadosNaoEncontradosException;

@Service
public class ControlePontoServico {

    private final FrequenciaRepositorioPorta frequenciaRepositorioPorta;

    @Autowired
    public ControlePontoServico(FrequenciaRepositorioPorta frequenciaRepositorioPorta) {
        this.frequenciaRepositorioPorta = frequenciaRepositorioPorta;
    }

    public Frequencia marcarEntrada(Long idUsuario, String nome) {
        Optional<Frequencia> funcionarioOpt = frequenciaRepositorioPorta.buscarPorIdUsuario(idUsuario);
        Frequencia funcionario = funcionarioOpt.orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        if(funcionario.getEntrada() != null ) {
            throw new IllegalArgumentException("Entrada já registrada em: " + funcionario.getEntrada());
        } else {
            funcionario.setEntrada(LocalDateTime.now());
            return frequenciaRepositorioPorta.salvar(funcionario);
        }
    }

    public Frequencia marcarSaidaAlmoco(Long idUsuario) {
        Optional<Frequencia> optionalFrequencia = frequenciaRepositorioPorta.buscarPorIdUsuario(idUsuario);
        Frequencia frequencia = optionalFrequencia.orElseThrow(() -> new IllegalArgumentException("Frequência não encontrada para o usuário e data fornecidos."));
        if(frequencia.getEntrada() != null ) {
            if(frequencia.getSaidaAlmoco() != null ) {
                throw new IllegalArgumentException("Saída Almoco já registrada em: " + frequencia.getSaidaAlmoco());
            } else {
                frequencia.setSaidaAlmoco(LocalDateTime.now());
                return frequenciaRepositorioPorta.salvar(frequencia);
            }
        } else {
            throw new IllegalArgumentException("Funcionário não tem registro de entrada");
        }
    }

    public Frequencia marcarEntradaAlmoco(Long idUsuario) {
        Optional<Frequencia> optionalFrequencia = frequenciaRepositorioPorta.buscarPorIdUsuario(idUsuario);
        Frequencia frequencia = optionalFrequencia.orElseThrow(() -> new IllegalArgumentException("Frequência não encontrada para o usuário e data fornecidos."));
        if(frequencia.getEntrada() != null ) {
            if(frequencia.getEntradaAlmoco() != null ) {
                throw new IllegalArgumentException("Entrada Almoco já registrada em: " + frequencia.getEntradaAlmoco());
            } else {
                frequencia.setEntradaAlmoco(LocalDateTime.now());
                return frequenciaRepositorioPorta.salvar(frequencia);
            }
        } else {
            throw new IllegalArgumentException("Funcionário não tem registro de entrada");
        }
    }

    public Frequencia marcarSaida(Long idUsuario) {
        Optional<Frequencia> optionalFrequencia = frequenciaRepositorioPorta.buscarPorIdUsuario(idUsuario);
        Frequencia frequencia = optionalFrequencia.orElseThrow(() -> new IllegalArgumentException("Frequência não encontrada para o usuário e data fornecidos."));
        if(frequencia.getEntrada() != null ) {
            if(frequencia.getSaida() != null ) {
                throw new IllegalArgumentException("Saída já registrada em: " + frequencia.getSaida());
            } else {
                // Garante que a saida seja no mesmo dia da entrada
                LocalDateTime now = LocalDateTime.now();
                if (now.toLocalDate().isAfter(frequencia.getEntrada().toLocalDate())) {
                    frequencia.setSaida(frequencia.getEntrada().withHour(17).withMinute(0).withSecond(0));
                } else {
                    frequencia.setSaida(now);
                }
                frequencia.calcularHorasTrabalhadas();
                return frequenciaRepositorioPorta.salvar(frequencia);
            }
        } else {
            throw new IllegalArgumentException("Funcionário não tem registro de entrada");
        }
    }

    public List<Frequencia> listarFrequencias() {
        List<Frequencia> list = frequenciaRepositorioPorta.buscarTodas();
        if (list.isEmpty()) {
            throw new DadosNaoEncontradosException("Não há dados para listar");
        }
        return list;
    }
}