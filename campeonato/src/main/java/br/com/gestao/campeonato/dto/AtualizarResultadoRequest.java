package br.com.gestao.campeonato.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
@Valid
public class AtualizarResultadoRequest {

    @NotBlank(message = "O resultado é obrigatório")
    @Pattern(
            regexp = "\\d+x\\d+",
            message = "Formato inválido. Use o padrão 3x1"
    )
    private String novoResultado;

    @NotNull(message = "O usuário é obrigatório")
    private Integer usuarioId;

    public String getNovoResultado() {
        return novoResultado;
    }

    public void setNovoResultado(String novoResultado) {
        this.novoResultado = novoResultado;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
