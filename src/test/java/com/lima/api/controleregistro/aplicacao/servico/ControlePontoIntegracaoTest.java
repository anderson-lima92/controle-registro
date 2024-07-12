package com.lima.api.controleregistro.aplicacao.servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima.api.controleregistro.dto.FrequenciaDTO;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControlePontoIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FrequenciaDTO frequenciaDTO;

    @BeforeEach
    void setUp() {
        frequenciaDTO = new FrequenciaDTO();
        frequenciaDTO.setIdUsuario(1L);
        frequenciaDTO.setNome("teste");
    }

    @Test
    void testMarcarEntrada() throws Exception {
        mockMvc.perform(post("/api/v1/frequencias/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/frequencias"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("entrada"));
    }

    @Test
    void testMarcarSaidaAlmoco() throws Exception {
        mockMvc.perform(post("/api/v1/frequencias/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/frequencias/saida-almoco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/frequencias"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("saidaAlmoco"));
    }

    @Test
    void testMarcarEntradaAlmoco() throws Exception {
        mockMvc.perform(post("/api/v1/frequencias/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/frequencias/saida-almoco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/frequencias/entrada-almoco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/frequencias"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("entradaAlmoco"));
    }

    @Test
    void testMarcarSaida() throws Exception {
        mockMvc.perform(post("/api/v1/frequencias/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/frequencias/saida-almoco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/frequencias/entrada-almoco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/frequencias/saida")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(frequenciaDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/frequencias"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("saida"));
    }

    @Test
    void testListarFrequencias() throws Exception {
        mockMvc.perform(get("/api/v1/frequencias"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("teste"));
    }
}
