package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.dto.ClassificacaoEquipeDTO;
import java.util.List;

public interface ClassificacaoService {

    List<ClassificacaoEquipeDTO> gerarClassificacao(Integer campeonatoId);

}
