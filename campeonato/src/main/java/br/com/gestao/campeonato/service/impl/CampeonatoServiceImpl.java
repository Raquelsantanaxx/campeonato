package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.entity.Formatocampeonato;
import br.com.gestao.campeonato.repository.CampeonatoRepository;
import br.com.gestao.campeonato.repository.PartidaRepository;
import br.com.gestao.campeonato.repository.SetPartidaRepository;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.EquipeService;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final EquipeService equipeService;
    private final PartidaService partidaService;
    public CampeonatoServiceImpl(CampeonatoRepository campeonatoRepository,
                                 EquipeService equipeService,
                                 PartidaService partidaService) {
        this.campeonatoRepository = campeonatoRepository;
        this.equipeService = equipeService;
        this.partidaService = partidaService;
    }

    @Override
    public Campeonato salvar(Campeonato campeonato) {
        validarCampeonato(campeonato);
        return campeonatoRepository.save(campeonato);
    }

    @Override
    public Campeonato buscarPorId(Integer id) {
        return campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
    }

    @Override
    public List<Campeonato> listarTodos() {
        return campeonatoRepository.findAll();
    }

    @Override
    public Campeonato atualizar(Integer id, Campeonato campeonato) {

        Campeonato campeonatoExistente = buscarPorId(id);

        // REGRA: formato não pode ser alterado
        if (!campeonatoExistente.getFormato().equals(campeonato.getFormato())) {
            throw new RuntimeException("Não é permitido alterar o formato do campeonato após a criação.");
        }

        validarCampeonato(campeonato);

        campeonatoExistente.setNome(campeonato.getNome());
        campeonatoExistente.setDataInicio(campeonato.getDataInicio());
        campeonatoExistente.setDataFim(campeonato.getDataFim());
        campeonatoExistente.setOrganizador(campeonato.getOrganizador());

        return campeonatoRepository.save(campeonatoExistente);
    }

    @Override
    public void desativar(Integer id) {
        Campeonato campeonato = buscarPorId(id);
        campeonato.setAtivo(false);
        campeonatoRepository.save(campeonato);
    }
    private void validarCampeonato(Campeonato campeonato) {

        if (campeonato.getFormato() == null) {
            throw new RuntimeException("O formato do campeonato é obrigatório.");
        }

        if (campeonato.getDataInicio() == null) {
            throw new RuntimeException("A data de início é obrigatória.");
        }

        if (campeonato.getDataFim() != null &&
                campeonato.getDataFim().isBefore(campeonato.getDataInicio())) {
            throw new RuntimeException("A data final não pode ser anterior à data de início.");
        }
    }
    @Override
    public boolean isPontosCorridos(Integer campeonatoId) {
        Campeonato campeonato = buscarPorId(campeonatoId);
        return campeonato.getFormato() == Formatocampeonato.PONTOS_CORRIDOS;
    }
    @Override
    public void iniciarCampeonato(Integer campeonatoId) {

        Campeonato campeonato = buscarPorId(campeonatoId);

        // REGRA 1: campeonato ativo
        if (!campeonato.getAtivo()) {
            throw new RuntimeException("Campeonato está inativo.");
        }

        // REGRA 2: não pode iniciar duas vezes
        if (campeonato.getIniciado()) {
            throw new RuntimeException("Campeonato já foi iniciado.");
        }

        // REGRA 3: precisa ter equipes
        int quantidadeEquipes = equipeService
                .listarPorCampeonato(campeonatoId)
                .size();

        if (quantidadeEquipes < 2) {
            throw new RuntimeException(
                    "O campeonato precisa de pelo menos 2 equipes para iniciar."
            );
        }

        // REGRA 4: gerar partidas (PONTOS CORRIDOS)
        partidaService.gerarPartidasPontosCorridos(campeonatoId);

        // MARCA COMO INICIADO
        campeonato.setIniciado(true);
        campeonatoRepository.save(campeonato);
    }

    @Override
    public void encerrarCampeonato(Integer campeonatoId) {
        // IMPLEMENTAÇÃO FUTURA
        // Aqui depois vamos:
        // - validar se todas as partidas terminaram
        // - calcular campeão
        // - encerrar campeonato
    }
}
