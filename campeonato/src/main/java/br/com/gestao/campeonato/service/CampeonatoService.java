package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.dto.ClassificacaoEquipeDTO;
import br.com.gestao.campeonato.entity.Campeonato;

import java.util.List;

public interface CampeonatoService {

    Campeonato salvar(Campeonato campeonato);

    Campeonato buscarPorId(Integer id);

    List<Campeonato> listarTodos();

    Campeonato atualizar(Integer id, Campeonato campeonato);

    void desativar(Integer id);
}
