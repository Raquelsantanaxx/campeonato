package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.dto.PartidaDTO;
import br.com.gestao.campeonato.dto.RodadaDTO;
import br.com.gestao.campeonato.entity.*;
import br.com.gestao.campeonato.repository.*;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PartidaServiceImpl implements PartidaService {

    private final PartidaRepository partidaRepository;
    private final AuditoriaResultadoRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipeRepository equipeRepository;
    private final CampeonatoRepository campeonatoRepository;

    public PartidaServiceImpl(PartidaRepository partidaRepository,
                              AuditoriaResultadoRepository auditoriaRepository,
                              UsuarioRepository usuarioRepository,
                              EquipeRepository equipeRepository,
                              CampeonatoRepository campeonatoRepository) {
        this.partidaRepository = partidaRepository;
        this.auditoriaRepository = auditoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.equipeRepository = equipeRepository;
        this.campeonatoRepository = campeonatoRepository;
    }

    // ================================
    // CRUD BÁSICO
    // ================================

    @Override
    public Partida salvar(Partida partida) {

        if (partida.getCampeonato().getIniciado()) {
            throw new RuntimeException("Não é possível alterar partidas após iniciar o campeonato.");
        }

        if (partida.getCampeonato() == null || partida.getCampeonato().getId() == null) {
            throw new RuntimeException("Campeonato é obrigatório.");
        }

        if (partida.getEquipeMandante() == null || partida.getEquipeVisitante() == null) {
            throw new RuntimeException("Mandante e visitante são obrigatórios.");
        }

        if (partida.getEquipeMandante().getId()
                .equals(partida.getEquipeVisitante().getId())) {
            throw new RuntimeException("Uma equipe não pode jogar contra ela mesma.");
        }

        Campeonato campeonato = campeonatoRepository.findById(
                partida.getCampeonato().getId()
        ).orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));

        Equipe mandante = equipeRepository.findById(
                partida.getEquipeMandante().getId()
        ).orElseThrow(() -> new RuntimeException("Equipe mandante não encontrada"));

        Equipe visitante = equipeRepository.findById(
                partida.getEquipeVisitante().getId()
        ).orElseThrow(() -> new RuntimeException("Equipe visitante não encontrada"));

        if (!mandante.getCampeonato().getId().equals(campeonato.getId()) ||
                !visitante.getCampeonato().getId().equals(campeonato.getId())) {
            throw new RuntimeException("As equipes devem pertencer ao mesmo campeonato.");
        }

        if (partida.getDataHora() == null) {
            partida.setDataHora(LocalDateTime.now());
        }

        partida.setCampeonato(campeonato);
        partida.setEquipeMandante(mandante);
        partida.setEquipeVisitante(visitante);
        partida.setFinalizada(false);

        return partidaRepository.save(partida);
    }

    @Override
    public Partida buscarPorId(Integer id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));
    }

    @Override
    public List<Partida> listarTodos() {
        return partidaRepository.findAll();
    }

    @Override
    public void deletar(Integer id) {
        partidaRepository.deleteById(id);
    }

    @Override
    public List<Partida> buscarPorCampeonato(Integer idCampeonato) {
        return partidaRepository.findByCampeonatoId(idCampeonato);
    }

    @Override
    public List<Partida> buscarPorEquipe(Integer idEquipe) {
        return partidaRepository
                .findByEquipeMandante_IdOrEquipeVisitante_Id(idEquipe, idEquipe);
    }

    // ================================
    // RESULTADO MANUAL
    // ================================

    @Override
    public Partida atualizarResultadoManual(Integer partidaId,
                                            String novoResultado,
                                            Integer usuarioId) {

        Partida partida = buscarPorId(partidaId);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String resultadoAnterior = partida.getResultadoFinal();

        partida.setResultadoFinal(novoResultado);
        partida.setFinalizada(true);

        Partida salva = partidaRepository.save(partida);

        AuditoriaResultado auditoria = new AuditoriaResultado();
        auditoria.setPartida(salva);
        auditoria.setUsuario(usuario);
        auditoria.setJustificativa("Resultado alterado manualmente");
        auditoria.setDadosAnteriores(resultadoAnterior);
        auditoria.setDataHora(LocalDateTime.now());

        auditoriaRepository.save(auditoria);

        return salva;
    }

    // ================================
    // GERAR PARTIDAS - PONTOS CORRIDOS
    // ================================

    @Override
    public void gerarPartidasPontosCorridos(Integer campeonatoId,
                                            String emailUsuario) {

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));

        if (!partidaRepository.findByCampeonatoId(campeonatoId).isEmpty()) {
            throw new RuntimeException("Partidas já foram geradas.");
        }

        if (!campeonato.getOrganizador().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("Você não pode gerar partidas.");
        }

        if (campeonato.getIniciado()) {
            throw new RuntimeException("Campeonato já iniciado.");
        }

        List<Equipe> equipes = equipeRepository.findByCampeonatoId(campeonatoId);

        if (equipes.size() < 2) {
            throw new RuntimeException("É necessário no mínimo 2 equipes.");
        }

        int numeroRodada = 1;

        for (int i = 0; i < equipes.size(); i++) {
            for (int j = i + 1; j < equipes.size(); j++) {

                Partida partida = new Partida();
                partida.setCampeonato(campeonato);
                partida.setEquipeMandante(equipes.get(i));
                partida.setEquipeVisitante(equipes.get(j));
                partida.setFinalizada(false);
                partida.setNumeroRodada(numeroRodada);

                partidaRepository.save(partida);
            }
            numeroRodada++;
        }
    }

    // ================================
    // RODADAS
    // ================================

    @Override
    public List<RodadaDTO> listarRodadasPorCampeonato(Integer campeonatoId) {

        List<Partida> partidas =
                partidaRepository.findByCampeonatoIdOrderByNumeroRodadaAsc(campeonatoId);

        return montarRodadas(partidas);
    }

    private List<RodadaDTO> montarRodadas(List<Partida> partidas) {

        Map<Integer, List<Partida>> mapa = new LinkedHashMap<>();

        for (Partida p : partidas) {

            mapa
                    .computeIfAbsent(p.getNumeroRodada(), r -> new ArrayList<>())
                    .add(p);
        }

        List<RodadaDTO> rodadas = new ArrayList<>();

        for (Map.Entry<Integer, List<Partida>> entry : mapa.entrySet()) {

            List<PartidaDTO> partidasDTO = new ArrayList<>();

            for (Partida p : entry.getValue()) {

                partidasDTO.add(
                        new PartidaDTO(
                                p.getEquipeMandante().getNome(),
                                p.getEquipeVisitante().getNome()
                        )
                );
            }

            rodadas.add(new RodadaDTO(entry.getKey(), partidasDTO));
        }

        return rodadas;
    }

    // ================================
    // MATA-MATA
    // ================================

    @Override
    public void gerarPartidasMataMata(Integer campeonatoId) {

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));

        List<Equipe> equipes = equipeRepository.findByCampeonatoId(campeonatoId);

        if (equipes.size() % 2 != 0) {
            throw new RuntimeException("Mata-mata exige número par de equipes.");
        }

        Collections.shuffle(equipes);

        for (int i = 0; i < equipes.size(); i += 2) {

            Partida partida = new Partida();
            partida.setCampeonato(campeonato);
            partida.setEquipeMandante(equipes.get(i));
            partida.setEquipeVisitante(equipes.get(i + 1));
            partida.setFinalizada(false);

            partidaRepository.save(partida);
        }
    }
    @Override
    public List<RodadaDTO> gerarRodadasPontosCorridos(Integer campeonatoId) {

        List<Partida> partidas =
                partidaRepository.findByCampeonatoIdOrderByNumeroRodadaAsc(campeonatoId);

        if (partidas.isEmpty()) {
            return Collections.emptyList();
        }

        return montarRodadas(partidas);
    }
}
