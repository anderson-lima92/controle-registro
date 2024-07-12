package com.lima.api.controleregistro.adaptador.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lima.api.controleregistro.aplicacao.porta.FrequenciaRepositorioPorta;
import com.lima.api.controleregistro.dominio.modelo.Frequencia;

@Component
public class FrequenciaRepositorioAdaptador implements FrequenciaRepositorioPorta {

    private final FrequenciaJpaRepositorio frequenciaJpaRepositorio;

    @Autowired
    public FrequenciaRepositorioAdaptador(FrequenciaJpaRepositorio frequenciaJpaRepositorio) {
        this.frequenciaJpaRepositorio = frequenciaJpaRepositorio;
    }

    @Override
    public Frequencia salvar(Frequencia frequencia) {
        return frequenciaJpaRepositorio.save(frequencia);
    }

    @Override
    public List<Frequencia> buscarTodas() {
        return frequenciaJpaRepositorio.findAll();
    }

    @Override
    public Optional<Frequencia> buscarPorIdUsuario(Long idUsuario) {
        return frequenciaJpaRepositorio.findByIdUsuario(idUsuario);
    }
}
