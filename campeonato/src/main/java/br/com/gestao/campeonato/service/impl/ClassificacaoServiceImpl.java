package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.dto.ClassificacaoEquipeDTO;
import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.entity.SetPartida;
import br.com.gestao.campeonato.repository.EquipeRepository;
import br.com.gestao.campeonato.repository.PartidaRepository;
import br.com.gestao.campeonato.repository.SetPartidaRepository;
import br.com.gestao.campeonato.service.ClassificacaoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassificacaoServiceImpl implements ClassificacaoService {

    private final EquipeRepository equipeRepository;
    private final PartidaRepository partidaRepository;
    private final SetPartidaRepository setPartidaRepository;

    public ClassificacaoServiceImpl(EquipeRepository equipeRepository,
                                    PartidaRepository partidaRepository,
                                    SetPartidaRepository setPartidaRepository) {
        this.equipeRepository = equipeRepository;
        this.partidaRepository = partidaRepository;
        this.setPartidaRepository = setPartidaRepository;
    }

    @Override
    public List<ClassificacaoEquipeDTO> gerarClassificacao(Integer campeonatoId) {

        // Buscar dados
        List<Equipe> listaEquipes = equipeRepository.findByCampeonatoId(campeonatoId);
        List<Partida> listaPartidas = partidaRepository.findByCampeonatoId(campeonatoId);

        // Lista final
        List<ClassificacaoEquipeDTO> classificacao = new ArrayList<>();

        // LOOP DAS EQUIPES
        for (int i = 0; i < listaEquipes.size(); i++) {

            Equipe equipeAtual = listaEquipes.get(i);

            ClassificacaoEquipeDTO dto = new ClassificacaoEquipeDTO();

            dto.setEquipeId(equipeAtual.getId());
            dto.setNomeEquipe(equipeAtual.getNome());
            dto.setPontosClassificacao(0);

            int jogos = 0;
            int vitorias = 0;
            int derrotas = 0;
            int setsPro = 0;
            int setsContra = 0;
            int pontosPro = 0;
            int pontosContra = 0;

            // LOOP DAS PARTIDAS
            for (int j = 0; j < listaPartidas.size(); j++) {

                Partida partidaAtual = listaPartidas.get(j);

                // só conta partida finalizada
                if (partidaAtual.getFinalizada() == true) {

                    boolean equipeParticipou = false;

                    // verificar se é mandante
                    if (partidaAtual.getEquipeMandante().getId().equals(equipeAtual.getId())) {
                        equipeParticipou = true;
                    }

                    // verificar se é visitante
                    if (partidaAtual.getEquipeVisitante().getId().equals(equipeAtual.getId())) {
                        equipeParticipou = true;
                    }

                    // se participou
                    if (equipeParticipou == true) {

                        jogos++;

                        List<SetPartida> listaSets =
                                setPartidaRepository.findByPartidaId(partidaAtual.getId());

                        int setsEquipe = 0;
                        int setsAdversario = 0;

                        // LOOP DOS SETS
                        for (int k = 0; k < listaSets.size(); k++) {

                            SetPartida setAtual = listaSets.get(k);

                            boolean equipeMandante = false;

                            if (partidaAtual.getEquipeMandante().getId().equals(equipeAtual.getId())) {
                                equipeMandante = true;
                            }

                            int meusPontos;
                            int pontosOponente;

                            if (equipeMandante == true) {
                                meusPontos = setAtual.getPontosMandante();
                                pontosOponente = setAtual.getPontosVisitante();
                            } else {
                                meusPontos = setAtual.getPontosVisitante();
                                pontosOponente = setAtual.getPontosMandante();
                            }

                            pontosPro = pontosPro + meusPontos;
                            pontosContra = pontosContra + pontosOponente;

                            if (meusPontos > pontosOponente) {
                                setsEquipe++;
                            } else {
                                setsAdversario++;
                            }
                        }

                        setsPro = setsPro + setsEquipe;
                        setsContra = setsContra + setsAdversario;

                        // vitória ou derrota
                        if (setsEquipe > setsAdversario) {
                            vitorias++;
                        } else {
                            derrotas++;
                        }

                        // pontuação oficial
                        int pontosClassificacaoPartida = 0;

                        if (setsEquipe > setsAdversario) {

                            if (setsEquipe == 3 && setsAdversario <= 1) {
                                pontosClassificacaoPartida = 3;
                            }

                            if (setsEquipe == 3 && setsAdversario == 2) {
                                pontosClassificacaoPartida = 2;
                            }

                        } else {

                            if (setsAdversario == 3 && setsEquipe == 2) {
                                pontosClassificacaoPartida = 1;
                            }
                        }

                        dto.setPontosClassificacao(
                                dto.getPontosClassificacao() + pontosClassificacaoPartida
                        );
                    }
                }
            }

            dto.setJogos(jogos);
            dto.setVitorias(vitorias);
            dto.setDerrotas(derrotas);
            dto.setSetsPro(setsPro);
            dto.setSetsContra(setsContra);
            dto.setPontosPro(pontosPro);
            dto.setPontosContra(pontosContra);

            classificacao.add(dto);
        }

        // ORDENAÇÃO MANUAL
        for (int i = 0; i < classificacao.size(); i++) {

            for (int j = i + 1; j < classificacao.size(); j++) {

                ClassificacaoEquipeDTO a = classificacao.get(i);
                ClassificacaoEquipeDTO b = classificacao.get(j);

                int saldoSetsA = a.getSetsPro() - a.getSetsContra();
                int saldoSetsB = b.getSetsPro() - b.getSetsContra();

                int saldoPontosA = a.getPontosPro() - a.getPontosContra();
                int saldoPontosB = b.getPontosPro() - b.getPontosContra();

                boolean trocar = false;

                if (b.getPontosClassificacao() > a.getPontosClassificacao()) {
                    trocar = true;
                }
                else if (b.getPontosClassificacao() == a.getPontosClassificacao()
                        && saldoSetsB > saldoSetsA) {
                    trocar = true;
                }
                else if (b.getPontosClassificacao() == a.getPontosClassificacao()
                        && saldoSetsB == saldoSetsA
                        && saldoPontosB > saldoPontosA) {
                    trocar = true;
                }

                if (trocar == true) {
                    classificacao.set(i, b);
                    classificacao.set(j, a);
                }
            }
        }

        return classificacao;
    }
}


