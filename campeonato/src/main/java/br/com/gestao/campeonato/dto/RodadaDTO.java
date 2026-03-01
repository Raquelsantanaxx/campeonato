package br.com.gestao.campeonato.dto;

import java.util.List;

public class RodadaDTO {

    private Integer numeroRodada;
    private List<PartidaDTO> partidas;

    public RodadaDTO(Integer numeroRodada, List<PartidaDTO> partidas) {
        this.numeroRodada = numeroRodada;
        this.partidas = partidas;
    }

    public Integer getNumeroRodada() {
        return numeroRodada;
    }

    public List<PartidaDTO> getPartidas() {
        return partidas;
    }
}