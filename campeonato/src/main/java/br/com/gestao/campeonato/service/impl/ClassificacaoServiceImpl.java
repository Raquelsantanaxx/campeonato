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

        List<Equipe> listaEquipes = equipeRepository.findByCampeonatoId(campeonatoId);
        List<Partida> listaPartidas = partidaRepository.findByCampeonatoId(campeonatoId);

        List<ClassificacaoEquipeDTO> classificacao = new ArrayList<>();

        for (Equipe equipeAtual : listaEquipes) {

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

            for (Partida partidaAtual : listaPartidas) {

                if (Boolean.TRUE.equals(partidaAtual.getFinalizada())) {

                    boolean equipeParticipou =
                            partidaAtual.getEquipeMandante().getId().equals(equipeAtual.getId())
                                    || partidaAtual.getEquipeVisitante().getId().equals(equipeAtual.getId());

                    if (equipeParticipou) {

                        jogos++;

                        List<SetPartida> listaSets =
                                setPartidaRepository.findByPartidaId(partidaAtual.getId());

                        int setsEquipe = 0;
                        int setsAdversario = 0;

                        boolean equipeMandante =
                                partidaAtual.getEquipeMandante().getId().equals(equipeAtual.getId());

                        for (SetPartida setAtual : listaSets) {

                            int meusPontos;
                            int pontosOponente;

                            if (equipeMandante) {
                                meusPontos = setAtual.getPontosMandante();
                                pontosOponente = setAtual.getPontosVisitante();
                            } else {
                                meusPontos = setAtual.getPontosVisitante();
                                pontosOponente = setAtual.getPontosMandante();
                            }

                            pontosPro += meusPontos;
                            pontosContra += pontosOponente;

                            if (meusPontos > pontosOponente) {
                                setsEquipe++;
                            } else {
                                setsAdversario++;
                            }
                        }

                        setsPro += setsEquipe;
                        setsContra += setsAdversario;

                        if (setsEquipe > setsAdversario) {
                            vitorias++;
                        } else {
                            derrotas++;
                        }

                        int pontosPartida = 0;

                        if (setsEquipe > setsAdversario) {
                            if (setsEquipe == 3 && setsAdversario <= 1) {
                                pontosPartida = 3;
                            } else if (setsEquipe == 3 && setsAdversario == 2) {
                                pontosPartida = 2;
                            }
                        } else {
                            if (setsAdversario == 3 && setsEquipe == 2) {
                                pontosPartida = 1;
                            }
                        }

                        dto.setPontosClassificacao(
                                dto.getPontosClassificacao() + pontosPartida
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

        // ================================
        // ORDENAÇÃO PROFISSIONAL
        // ================================

        // ================================
// ORDENAÇÃO PROFISSIONAL
// ================================

        for (int i = 0; i < classificacao.size(); i++) {

            for (int j = i + 1; j < classificacao.size(); j++) {

                ClassificacaoEquipeDTO a = classificacao.get(i);
                ClassificacaoEquipeDTO b = classificacao.get(j);

                int saldoSetsA = a.getSetsPro() - a.getSetsContra();
                int saldoSetsB = b.getSetsPro() - b.getSetsContra();

                int saldoPontosA = a.getPontosPro() - a.getPontosContra();
                int saldoPontosB = b.getPontosPro() - b.getPontosContra();

                boolean trocar = false;

                // 1️⃣ Pontos
                if (b.getPontosClassificacao() > a.getPontosClassificacao()) {
                    trocar = true;
                }

                // 2️⃣ Vitórias
                else if (b.getPontosClassificacao() == a.getPontosClassificacao()
                        && b.getVitorias() > a.getVitorias()) {
                    trocar = true;
                }

                // 3️⃣ Saldo de Sets
                else if (b.getPontosClassificacao() == a.getPontosClassificacao()
                        && b.getVitorias() == a.getVitorias()
                        && saldoSetsB > saldoSetsA) {
                    trocar = true;
                }

                // 4️⃣ Saldo de Pontos
                else if (b.getPontosClassificacao() == a.getPontosClassificacao()
                        && b.getVitorias() == a.getVitorias()
                        && saldoSetsB == saldoSetsA
                        && saldoPontosB > saldoPontosA) {
                    trocar = true;
                }

                if (trocar) {
                    classificacao.set(i, b);
                    classificacao.set(j, a);
                }
            }
        }

        return classificacao;
    }
}


