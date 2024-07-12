package com.lima.api.controleregistro.aplicacao.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lima.api.controleregistro.aplicacao.porta.FrequenciaRepositorioPorta;
import com.lima.api.controleregistro.dominio.modelo.Frequencia;

class ControlePontoServicoTest {

    @InjectMocks
    private ControlePontoServico controlePontoServico;

    @Mock
    private FrequenciaRepositorioPorta frequenciaRepositorioPorta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarcarEntrada() {
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setNome("Test User");

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));
        when(frequenciaRepositorioPorta.salvar(frequencia)).thenReturn(frequencia);

        Frequencia frequenciaSalva = controlePontoServico.marcarEntrada(1L, "Test User");

        assertNotNull(frequenciaSalva);
        assertNotNull(frequenciaSalva.getEntrada());
        assertEquals("Test User", frequenciaSalva.getNome());
    }
    
    @Test
    void testMarcarEntradaJaRegistrada() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setNome("Test User");
        frequencia.setEntrada(now);

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarEntrada(1L, "Test User");
        });

        assertEquals("Entrada já registrada em: " + now, exception.getMessage());
    }
    
    @Test
    void testMarcarEntradaComErro() {
        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarEntrada(1L, "Test User");
        });

        assertEquals("Funcionário não encontrado.", exception.getMessage());
    }

    @Test
    void testMarcarSaidaAlmoco() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setEntrada(now);

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));
        when(frequenciaRepositorioPorta.salvar(frequencia)).thenReturn(frequencia);

        Frequencia frequenciaAtualizada = controlePontoServico.marcarSaidaAlmoco(1L);

        assertNotNull(frequenciaAtualizada);
        assertNotNull(frequenciaAtualizada.getSaidaAlmoco());
        assertEquals(frequencia.getEntrada(), frequenciaAtualizada.getEntrada());
    }
    
    @Test
    void testMarcarSaidaAlmocoJaRegistrada() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setEntrada(now);
        frequencia.setSaidaAlmoco(now.plusHours(4));

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarSaidaAlmoco(1L);
        });

        assertEquals("Saída Almoco já registrada em: " + frequencia.getSaidaAlmoco(), exception.getMessage());
    }


    @Test
    void testMarcarSaidaAlmocoComErro() {
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarSaidaAlmoco(1L);
        });

        assertEquals("Funcionário não tem registro de entrada", exception.getMessage());
    }

    @Test
    void testMarcarEntradaAlmoco() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setEntrada(now);
        frequencia.setSaidaAlmoco(now.plusHours(4));

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));
        when(frequenciaRepositorioPorta.salvar(frequencia)).thenReturn(frequencia);

        Frequencia frequenciaAtualizada = controlePontoServico.marcarEntradaAlmoco(1L);

        assertNotNull(frequenciaAtualizada);
        assertNotNull(frequenciaAtualizada.getEntradaAlmoco());
        assertEquals(frequencia.getEntrada(), frequenciaAtualizada.getEntrada());
    }
    
    @Test
    void testMarcarEntradaAlmocoJaRegistrada() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setEntrada(now);
        frequencia.setSaidaAlmoco(now.plusHours(4));
        frequencia.setEntradaAlmoco(now.plusHours(5));

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarEntradaAlmoco(1L);
        });

        assertEquals("Entrada Almoco já registrada em: " + frequencia.getEntradaAlmoco(), exception.getMessage());
    }


    @Test
    void testMarcarEntradaAlmocoComErro() {
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarEntradaAlmoco(1L);
        });

        assertEquals("Funcionário não tem registro de entrada", exception.getMessage());
    }

    @Test
    void testMarcarSaida() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        LocalDateTime saidaAlmoco = now.plusHours(4);
        LocalDateTime entradaAlmoco = saidaAlmoco.plusHours(1);
        LocalDateTime saida = entradaAlmoco.plusHours(4);

        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setEntrada(now);
        frequencia.setSaidaAlmoco(saidaAlmoco);
        frequencia.setEntradaAlmoco(entradaAlmoco);

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));
        when(frequenciaRepositorioPorta.salvar(frequencia)).thenAnswer(invocation -> {
            Frequencia arg = invocation.getArgument(0);
            arg.setSaida(saida);
            arg.calcularHorasTrabalhadas();
            return arg;
        });

        Frequencia frequenciaAtualizada = controlePontoServico.marcarSaida(1L);

        assertNotNull(frequenciaAtualizada);
        assertNotNull(frequenciaAtualizada.getSaida());
        assertEquals(frequencia.getEntrada(), frequenciaAtualizada.getEntrada());
        assertEquals(8, frequenciaAtualizada.getHorasTrabalhadas());
    }
    
    @Test
    void testMarcarSaidaJaRegistrada() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        LocalDateTime saidaAlmoco = now.plusHours(4);
        LocalDateTime entradaAlmoco = saidaAlmoco.plusHours(1);
        LocalDateTime saida = entradaAlmoco.plusHours(4);

        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);
        frequencia.setEntrada(now);
        frequencia.setSaidaAlmoco(saidaAlmoco);
        frequencia.setEntradaAlmoco(entradaAlmoco);
        frequencia.setSaida(saida);

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarSaida(1L);
        });

        assertEquals("Saída já registrada em: " + frequencia.getSaida(), exception.getMessage());
    }


    
    @Test
    void testMarcarSaidaComErro() {
        Frequencia frequencia = new Frequencia();
        frequencia.setIdUsuario(1L);

        when(frequenciaRepositorioPorta.buscarPorIdUsuario(1L)).thenReturn(Optional.of(frequencia));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoServico.marcarSaida(1L);
        });

        assertEquals("Funcionário não tem registro de entrada", exception.getMessage());
    }

    @Test
    void testListarFrequencias() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Frequencia frequencia = new Frequencia();
        frequencia.setEntrada(now);
        frequencia.setSaida(now.plusHours(4));

        when(frequenciaRepositorioPorta.buscarTodas()).thenReturn(Collections.singletonList(frequencia));

        List<Frequencia> frequencias = controlePontoServico.listarFrequencias();

        assertNotNull(frequencias);
        assertEquals(1, frequencias.size());
        assertEquals(frequencia.getEntrada(), frequencias.get(0).getEntrada());
        assertEquals(frequencia.getSaida(), frequencias.get(0).getSaida());
    }
    
    @Test
    void testListarFrequenciasComVariosDias() {
        LocalDateTime dia1Entrada = LocalDateTime.of(2024, 7, 11, 8, 0);
        LocalDateTime dia1Saida = LocalDateTime.of(2024, 7, 11, 17, 0);
        Frequencia frequenciaDia1 = new Frequencia();
        frequenciaDia1.setId(1L);
        frequenciaDia1.setNome("teste");
        frequenciaDia1.setIdUsuario(15215L);
        frequenciaDia1.setEntrada(dia1Entrada);
        frequenciaDia1.setSaidaAlmoco(dia1Entrada.plusHours(4));
        frequenciaDia1.setEntradaAlmoco(dia1Entrada.plusHours(5));
        frequenciaDia1.setSaida(dia1Saida);
        frequenciaDia1.calcularHorasTrabalhadas();

        LocalDateTime dia2Entrada = LocalDateTime.of(2024, 7, 12, 8, 0);
        LocalDateTime dia2Saida = LocalDateTime.of(2024, 7, 12, 17, 0);
        Frequencia frequenciaDia2 = new Frequencia();
        frequenciaDia2.setId(2L);
        frequenciaDia2.setNome("teste");
        frequenciaDia2.setIdUsuario(15215L);
        frequenciaDia2.setEntrada(dia2Entrada);
        frequenciaDia2.setSaidaAlmoco(dia2Entrada.plusHours(4));
        frequenciaDia2.setEntradaAlmoco(dia2Entrada.plusHours(5));
        frequenciaDia2.setSaida(dia2Saida);
        frequenciaDia2.calcularHorasTrabalhadas();

        when(frequenciaRepositorioPorta.buscarTodas()).thenReturn(Arrays.asList(frequenciaDia1, frequenciaDia2));

        List<Frequencia> frequencias = controlePontoServico.listarFrequencias();

        assertNotNull(frequencias);
        assertEquals(2, frequencias.size());
        assertEquals(frequenciaDia1.getEntrada(), frequencias.get(0).getEntrada());
        assertEquals(frequenciaDia1.getSaida(), frequencias.get(0).getSaida());
        assertEquals(frequenciaDia2.getEntrada(), frequencias.get(1).getEntrada());
        assertEquals(frequenciaDia2.getSaida(), frequencias.get(1).getSaida());
    }
}
