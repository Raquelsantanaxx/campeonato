package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.entity.Formatocampeonato;
import br.com.gestao.campeonato.entity.Usuario;
import br.com.gestao.campeonato.repository.CampeonatoRepository;
import br.com.gestao.campeonato.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;

    public CampeonatoServiceImpl(CampeonatoRepository campeonatoRepository,
                                 EquipeService equipeService,
                                 PartidaService partidaService,
                                 UsuarioRepository usuarioRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.equipeService = equipeService;
        this.partidaService = partidaService;
        this.usuarioRepository = usuarioRepository;
    }

    // ===============================
    // SALVAR
    // ===============================
    @Override
    public Campeonato salvar(Campeonato campeonato, String emailUsuario) {

        Usuario organizador = usuarioRepository
                .findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        campeonato.setOrganizador(organizador);
        campeonato.setAtivo(true);
        campeonato.setIniciado(false);

        validarCampeonato(campeonato);

        return campeonatoRepository.save(campeonato);
    }

    // ===============================
    // BUSCAR
    // ===============================
    @Override
    public Campeonato buscarPorId(Integer id) {
        return campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
    }

    @Override
    public List<Campeonato> listarTodos() {
        return campeonatoRepository.findAll();
    }

    // ===============================
    // ATUALIZAR (SOMENTE DONO)
    // ===============================
    @Override
    public Campeonato atualizar(Integer id, Campeonato campeonato, String emailUsuario) {

        Campeonato existente = buscarPorId(id);

        if (!existente.getOrganizador().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("Você não tem permissão para editar este campeonato.");
        }

        existente.setNome(campeonato.getNome());
        existente.setDataInicio(campeonato.getDataInicio());
        existente.setDataFim(campeonato.getDataFim());

        return campeonatoRepository.save(existente);
    }

    // ===============================
    // DESATIVAR (SOMENTE DONO)
    // ===============================
    @Override
    public void desativar(Integer id, String emailUsuario) {

        Campeonato campeonato = buscarPorId(id);

        if (!campeonato.getOrganizador().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("Você não pode desativar este campeonato.");
        }

        campeonato.setAtivo(false);
        campeonatoRepository.save(campeonato);
    }

    // ===============================
    // INICIAR (SOMENTE DONO)
    // ===============================
    @Override
    public void iniciarCampeonato(Integer campeonatoId,
                                  String emailUsuario) {

        Campeonato campeonato = buscarPorId(campeonatoId);

        validarDono(campeonato, emailUsuario);

        if (!campeonato.getAtivo()) {
            throw new RuntimeException("Campeonato está inativo.");
        }

        if (campeonato.getIniciado()) {
            throw new RuntimeException("Campeonato já foi iniciado.");
        }

        int quantidadeEquipes =
                equipeService.listarPorCampeonato(campeonatoId).size();

        if (quantidadeEquipes < 2) {
            throw new RuntimeException(
                    "O campeonato precisa de pelo menos 2 equipes."
            );
        }

        if (campeonato.getFormato() == Formatocampeonato.PONTOS_CORRIDOS) {
            partidaService.gerarPartidasPontosCorridos(campeonatoId);
        } else if (campeonato.getFormato() == Formatocampeonato.MATA_MATA) {
            partidaService.gerarPartidasMataMata(campeonatoId);
        }

        campeonato.setIniciado(true);
        campeonatoRepository.save(campeonato);
    }

    @Override
    public void encerrarCampeonato(Integer campeonatoId) {
        // implementação futura
    }

    @Override
    public boolean isPontosCorridos(Integer campeonatoId) {
        Campeonato campeonato = buscarPorId(campeonatoId);
        return campeonato.getFormato() == Formatocampeonato.PONTOS_CORRIDOS;
    }

    // ===============================
    // VALIDAÇÕES
    // ===============================

    private void validarCampeonato(Campeonato campeonato) {

        if (campeonato.getFormato() == null) {
            throw new RuntimeException("Formato obrigatório.");
        }

        if (campeonato.getDataInicio() == null) {
            throw new RuntimeException("Data de início obrigatória.");
        }

        if (campeonato.getDataFim() != null &&
                campeonato.getDataFim()
                        .isBefore(campeonato.getDataInicio())) {

            throw new RuntimeException(
                    "Data final não pode ser anterior à inicial."
            );
        }
    }

    private void validarDono(Campeonato campeonato,
                             String emailUsuario) {

        if (!campeonato.getOrganizador()
                .getEmail()
                .equals(emailUsuario)) {

            throw new RuntimeException(
                    "Você não tem permissão para alterar este campeonato."
            );
        }
    }
}
