package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.entity.CriterioDeDesempate;


import java.util.List;
import java.util.Optional;

public interface CriterioDeDesempateService {

    CriterioDeDesempate salvar(CriterioDeDesempate criterio);

    Optional<CriterioDeDesempate> buscarPorId(Integer id);

    List<CriterioDeDesempate> listarTodos();

    List<CriterioDeDesempate> listarPorCampeonato(Integer campeonatoId);

    void deletar(Integer id);
}

