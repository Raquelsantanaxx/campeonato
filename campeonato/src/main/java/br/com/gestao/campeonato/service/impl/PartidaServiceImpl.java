package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.AuditoriaResultado;
import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.entity.Usuario;
import br.com.gestao.campeonato.repository.AuditoriaResultadoRepository;
import br.com.gestao.campeonato.repository.PartidaRepository;
import br.com.gestao.campeonato.repository.UsuarioRepository;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PartidaServiceImpl implements PartidaService {

    private final PartidaRepository partidaRepository;
    private final AuditoriaResultadoRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;



    public PartidaServiceImpl(PartidaRepository partidaRepository,
                              AuditoriaResultadoRepository auditoriaRepository,
                              UsuarioRepository usuarioRepository) {
        this.partidaRepository = partidaRepository;
        this.auditoriaRepository = auditoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Partida salvar(Partida partida) {

        if (partida.getDataHora() == null) {
            partida.setDataHora(LocalDateTime.now());
        }

        return partidaRepository.save(partida);
    }


    @Override
    public Optional<Partida> buscarPorId(Integer id) {
        return partidaRepository.findById(id);
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
        return partidaRepository.findByEquipeMandante_IdOrEquipeVisitante_Id(idEquipe, idEquipe);
    }

    @Override
    public Partida atualizarResultadoManual(Integer partidaId,
                                            String novoResultado,
                                            Integer usuarioId) {


        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String resultadoAnterior = partida.getResultadoFinal();


        partida.setResultadoFinal(novoResultado);
        partida.setFinalizada(true);


        Partida partidaSalva = partidaRepository.save(partida);

        AuditoriaResultado auditoria = new AuditoriaResultado();
        auditoria.setPartida(partida);
        auditoria.setUsuario(usuario);
        auditoria.setJustificativa("Resultado alterado manualmente pelo sistema");
        auditoria.setDadosAnteriores(resultadoAnterior);
        auditoria.setDataHora(LocalDateTime.now());

        auditoriaRepository.save(auditoria);

        return partidaSalva;
    }

}

