package com.lima.api.controleregistro.adaptador.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lima.api.controleregistro.aplicacao.servico.ControlePontoServico;
import com.lima.api.controleregistro.dominio.modelo.Frequencia;
import com.lima.api.controleregistro.dto.FrequenciaDTO;
import com.lima.api.controleregistro.dto.RespostaComMensagem;
import com.lima.api.controleregistro.exception.DadosNaoEncontradosException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/frequencias")
@Tag(name = "Controle de Frequências", description = "APIs para gerenciamento de frequências")
public class ControlePontoControlador {

    private final ControlePontoServico controlePontoServico;

    @Autowired
    public ControlePontoControlador(ControlePontoServico controlePontoServico) {
        this.controlePontoServico = controlePontoServico;
    }

    @Operation(summary = "Marcar entrada", description = "Marca a entrada do estagiário")
    @PostMapping("/entrada")
    public ResponseEntity<?> marcarEntrada(@RequestBody FrequenciaDTO frequenciaDTO) {
        try {
            Frequencia frequencia = controlePontoServico.marcarEntrada(frequenciaDTO.getIdUsuario(), frequenciaDTO.getNome());
            RespostaComMensagem<Frequencia> resposta = new RespostaComMensagem<>(frequencia, "Marcação de entrada realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Marcar saída para almoço", description = "Marca a saída para o almoço do estagiário")
    @PostMapping("/saida-almoco")
    public ResponseEntity<?> marcarSaidaAlmoco(@RequestBody FrequenciaDTO frequenciaDTO) {
        try {
            Frequencia frequencia = controlePontoServico.marcarSaidaAlmoco(frequenciaDTO.getIdUsuario());
            RespostaComMensagem<Frequencia> resposta = new RespostaComMensagem<>(frequencia, "Marcação de saída para almoço realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Marcar entrada após almoço", description = "Marca a entrada após o almoço do estagiário")
    @PostMapping("/entrada-almoco")
    public ResponseEntity<?> marcarEntradaAlmoco(@RequestBody FrequenciaDTO frequenciaDTO) {
        try {
            Frequencia frequencia = controlePontoServico.marcarEntradaAlmoco(frequenciaDTO.getIdUsuario());
            RespostaComMensagem<Frequencia> resposta = new RespostaComMensagem<>(frequencia, "Marcação de entrada após almoço realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Marcar saída", description = "Marca a saída do estagiário")
    @PostMapping("/saida")
    public ResponseEntity<?> marcarSaida(@RequestBody FrequenciaDTO frequenciaDTO) {
        try {
            Frequencia frequencia = controlePontoServico.marcarSaida(frequenciaDTO.getIdUsuario());
            RespostaComMensagem<Frequencia> resposta = new RespostaComMensagem<>(frequencia, "Marcação de saída realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Listar todas as frequências", description = "Lista todas as frequências registradas")
    @GetMapping
    public ResponseEntity<?> listarFrequencias() {
        try {
            List<Frequencia> frequencias = controlePontoServico.listarFrequencias();
            return ResponseEntity.ok(frequencias);
        } catch (DadosNaoEncontradosException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}
