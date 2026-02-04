package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.CriterioDeDesempate;

import br.com.gestao.campeonato.repository.CriterioDeDesempateRepository;
import br.com.gestao.campeonato.service.CriterioDeDesempateService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CriterioDeDesempateServiceImpl implements CriterioDeDesempateService {

    private final CriterioDeDesempateRepository criterioRepository;

    public CriterioDeDesempateServiceImpl(CriterioDeDesempateRepository criterioRepository) {
        this.criterioRepository = criterioRepository;
    }

    @Override
    public CriterioDeDesempate salvar(CriterioDeDesempate criterio) {
        return criterioRepository.save(criterio);
    }

    @Override
    public Optional<CriterioDeDesempate> buscarPorId(Integer id) {
        return criterioRepository.findById(id);
    }

    @Override
    public List<CriterioDeDesempate> listarTodos() {
        return criterioRepository.findAll();
    }

    @Override
    public List<CriterioDeDesempate> listarPorCampeonato(Integer campeonatoId) {
        return criterioRepository.findByCampeonatoId(campeonatoId);
    }

    @Override
    public void deletar(Integer id) {
        criterioRepository.deleteById(id);
    }
}

