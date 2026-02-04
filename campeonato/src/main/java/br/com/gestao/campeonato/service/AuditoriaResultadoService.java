package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.entity.AuditoriaResultado;

import java.util.List;
import java.util.Optional;

public interface AuditoriaResultadoService {

    AuditoriaResultado salvar(AuditoriaResultado auditoria);

    Optional<AuditoriaResultado> buscarPorId(Integer id);

    List<AuditoriaResultado> listarTodas();

    List<AuditoriaResultado> listarPorPartida(Integer partidaId);

    void deletar(Integer id);
}

