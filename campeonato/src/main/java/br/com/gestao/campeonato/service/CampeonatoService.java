package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.dto.ClassificacaoEquipeDTO;
import br.com.gestao.campeonato.entity.Campeonato;

import java.util.List;

public interface CampeonatoService {

    Campeonato salvar(Campeonato campeonato, String emailUsuario);

    Campeonato buscarPorId(Integer id);

    List<Campeonato> listarTodos();

    Campeonato atualizar(Integer id, Campeonato campeonato, String emailUsuario);

    void desativar(Integer id, String emailUsuario);

    boolean isPontosCorridos(Integer campeonatoId);

    void iniciarCampeonato(Integer id, String emailUsuario);

    void encerrarCampeonato(Integer campeonatoId);

    void gerarPartidas(Integer campeonatoId, String emailUsuario);
}

