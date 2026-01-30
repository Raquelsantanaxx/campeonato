package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.repository.CampeonatoRepository;
import br.com.gestao.campeonato.service.CampeonatoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;

    public CampeonatoServiceImpl(CampeonatoRepository campeonatoRepository) {
        this.campeonatoRepository = campeonatoRepository;
    }

    @Override
    public Campeonato salvar(Campeonato campeonato) {
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

        campeonatoExistente.setNome(campeonato.getNome());
        campeonatoExistente.setFormato(campeonato.getFormato());
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
}
