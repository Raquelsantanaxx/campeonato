package br.com.gestao.campeonato.dto;

public class PartidaDTO {

    private String mandante;
    private String visitante;

    public PartidaDTO(String mandante, String visitante) {
        this.mandante = mandante;
        this.visitante = visitante;
    }

    public String getMandante() {
        return mandante;
    }

    public String getVisitante() {
        return visitante;
    }
}
