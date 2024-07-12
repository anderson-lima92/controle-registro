package com.lima.api.controleregistro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaComMensagem<T> {
    private T dados;
    private String mensagem;
}
