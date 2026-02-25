package br.com.gestao.campeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipeResponseDTO {

    private Integer id;
    private String nome;
    private String sigla;
    private Integer campeonatoId;
    private String campeonatoNome;
}
