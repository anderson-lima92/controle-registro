package com.lima.api.controleregistro.aplicacao.porta;

import java.util.List;
import java.util.Optional;

import com.lima.api.controleregistro.dominio.modelo.Frequencia;

public interface FrequenciaRepositorioPorta {
    Frequencia salvar(Frequencia frequencia);
    List<Frequencia> buscarTodas();
    Optional<Frequencia> buscarPorIdUsuario(Long idUsuario);
}

