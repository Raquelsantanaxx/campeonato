package br.com.gestao.campeonato.controller;

import br.com.gestao.campeonato.entity.AuditoriaResultado;
import br.com.gestao.campeonato.service.AuditoriaResultadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoria_resultado")
public class AuditoriaResultadoController {

    private final AuditoriaResultadoService auditoriaService;

    public AuditoriaResultadoController(AuditoriaResultadoService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }


    @PostMapping
    public ResponseEntity<AuditoriaResultado> registrar(@RequestBody AuditoriaResultado auditoria) {
        AuditoriaResultado salvo = auditoriaService.salvar(auditoria);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaResultado>> listarTodas() {
        return ResponseEntity.ok(auditoriaService.listarTodas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaResultado> buscarPorId(@PathVariable Integer id) {
        return auditoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<AuditoriaResultado>> listarPorPartida(@PathVariable Integer partidaId) {
        return ResponseEntity.ok(auditoriaService.listarPorPartida(partidaId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        auditoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

