package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.AuditoriaResultado;
import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.entity.SetPartida;
import br.com.gestao.campeonato.entity.Usuario;
import br.com.gestao.campeonato.repository.AuditoriaResultadoRepository;
import br.com.gestao.campeonato.repository.PartidaRepository;
import br.com.gestao.campeonato.repository.SetPartidaRepository;
import br.com.gestao.campeonato.repository.UsuarioRepository;
import br.com.gestao.campeonato.service.SetPartidaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SetPartidaServiceImpl implements SetPartidaService {

    private final SetPartidaRepository setRepository;
    private final PartidaRepository partidaRepository;
    private final AuditoriaResultadoRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public SetPartidaServiceImpl(SetPartidaRepository setRepository,
                                 PartidaRepository partidaRepository,
                                 AuditoriaResultadoRepository auditoriaRepository,
                                 UsuarioRepository usuarioRepository) {
        this.setRepository = setRepository;
        this.partidaRepository = partidaRepository;
        this.auditoriaRepository = auditoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public SetPartida criarSet(Integer partidaId, SetPartida set) {


        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() ->
                        new RuntimeException("Partida não encontrada"));
        if (partida.getFinalizada()) {
            throw new RuntimeException("Não é possível adicionar set em partida finalizada");
        }

        if (set.getPontosMandante() == null || set.getPontosVisitante() == null) {
            throw new RuntimeException("Pontuação do set não pode ser nula");
        }

        if (set.getPontosMandante() < 0 || set.getPontosVisitante() < 0) {
            throw new RuntimeException("Pontuação do set não pode ser negativa");
        }


        if (set.getPontosMandante().equals(set.getPontosVisitante())) {
            throw new RuntimeException("Set não pode terminar empatado");
        }


        List<SetPartida> setsExistentes =
                setRepository.findByPartidaId(partida.getId());
        if (setsExistentes.size() >= 5) {
            throw new RuntimeException("Número máximo de sets atingido (melhor de 5)");
        }

        int setsMandante = 0;
        int setsVisitante = 0;

        for (SetPartida s : setsExistentes) {
            if (s.getPontosMandante() > s.getPontosVisitante()) {
                setsMandante++;
            } else {
                setsVisitante++;
            }
        }

        if (setsMandante == 3 || setsVisitante == 3) {
            throw new RuntimeException("A partida já possui um vencedor");
        }


        set.setPartida(partida);
        SetPartida setSalvo = setRepository.save(set);

        atualizarResultadoPartida(partida);

        return setSalvo;
    }

    @Override
    public List<SetPartida> listarSets(Integer partidaId) {
        return setRepository.findByPartidaId(partidaId);
    }

    private void atualizarResultadoPartida(Partida partida) {
        List<SetPartida> sets = setRepository.findByPartidaId(partida.getId());

        int setsMandante = 0;
        int setsVisitante = 0;

        for (SetPartida set : sets) {
            if (set.getPontosMandante() > set.getPontosVisitante()) {
                setsMandante++;
            } else {
                setsVisitante++;
            }
        }
        if (setsMandante == 3 || setsVisitante == 3) {
            partida.setFinalizada(true);
            String resultadoFinal = " ";

            if (setsMandante > setsVisitante) {
                partida.setResultadoFinal(setsMandante + "x" + setsVisitante);
            } else {
                partida.setResultadoFinal(setsVisitante + "x" + setsMandante);
            }

            partida.setResultadoFinal(resultadoFinal);
            partidaRepository.save(partida);

            Usuario usuarioSistema = usuarioRepository.findById(1)
                    .orElseThrow(() ->
                            new RuntimeException("Usuário do sistema não encontrado"));

            AuditoriaResultado auditoria = new AuditoriaResultado();
            auditoria.setPartida(partida);
            auditoria.setUsuario(usuarioSistema);
            auditoria.setJustificativa(
                    "Resultado final definido automaticamente pelo sistema"
            );
            auditoria.setDadosAnteriores(null);
            auditoria.setDataHora(LocalDateTime.now());

            auditoriaRepository.save(auditoria);
        }
    }
}

//pessoal ainda falta chamar os sets dentro 
// dentro do arquivo de partidas.
// tem que implementar para que as tabelas sejam
// configuradas automaticamente
// ontem só implementei até a crianção 
// de rodadas automática e acrescentei uma
// página fantasma. 

