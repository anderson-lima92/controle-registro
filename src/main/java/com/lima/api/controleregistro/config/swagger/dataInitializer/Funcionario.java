package com.lima.api.controleregistro.config.swagger.dataInitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lima.api.controleregistro.aplicacao.porta.FrequenciaRepositorioPorta;
import com.lima.api.controleregistro.dominio.modelo.Frequencia;

@Configuration
public class Funcionario {
	
    @Autowired
    private FrequenciaRepositorioPorta frequenciaRepositorioPorta;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {

            Frequencia usuarioPadrao = new Frequencia();
            usuarioPadrao.setIdUsuario(1L);
            usuarioPadrao.setNome("teste");

            frequenciaRepositorioPorta.salvar(usuarioPadrao);
        };
    }
}
