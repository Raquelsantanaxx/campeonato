package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.AuditoriaResultado;
import br.com.gestao.campeonato.repository.AuditoriaResultadoRepository;
import br.com.gestao.campeonato.service.AuditoriaResultadoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditoriaResultadoServiceImpl implements AuditoriaResultadoService {

    private final AuditoriaResultadoRepository auditoriaRepository;

    public AuditoriaResultadoServiceImpl(AuditoriaResultadoRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @Override
    public AuditoriaResultado salvar(AuditoriaResultado auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    @Override
    public Optional<AuditoriaResultado> buscarPorId(Integer id) {
        return auditoriaRepository.findById(id);
    }

    @Override
    public List<AuditoriaResultado> listarTodas() {
        return auditoriaRepository.findAll();
    }

    @Override
    public List<AuditoriaResultado> listarPorPartida(Integer partidaId) {
        return auditoriaRepository.findByPartidaId(partidaId);
    }

    @Override
    public void deletar(Integer id) {
        auditoriaRepository.deleteById(id);
    }
}
