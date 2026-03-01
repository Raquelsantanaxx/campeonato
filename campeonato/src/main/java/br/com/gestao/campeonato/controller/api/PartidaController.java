package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.dto.AtualizarResultadoRequest;
import br.com.gestao.campeonato.dto.RodadaDTO;
import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<Partida> cadastrar(@RequestBody Partida partida) {
        return ResponseEntity.ok(partidaService.salvar(partida));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @GetMapping
    public ResponseEntity<List<Partida>> listarTodas() {
        return ResponseEntity.ok(partidaService.listarTodos());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(partidaService.buscarPorId(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/campeonato/{idCampeonato}")
    public ResponseEntity<List<Partida>> buscarPorCampeonato(
            @PathVariable Integer idCampeonato) {
        return ResponseEntity.ok(partidaService.buscarPorCampeonato(idCampeonato));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/equipe/{idEquipe}")
    public ResponseEntity<List<Partida>> buscarPorEquipe(
            @PathVariable Integer idEquipe) {
        return ResponseEntity.ok(partidaService.buscarPorEquipe(idEquipe));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        partidaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR','ARBITRO')")
    @PutMapping("/{id}/resultado")
    public ResponseEntity<Partida> atualizarResultado(
            @PathVariable Integer id,
            @RequestBody @Valid AtualizarResultadoRequest request) {

        return ResponseEntity.ok(
                partidaService.atualizarResultadoManual(
                        id,
                        request.getNovoResultado(),
                        request.getUsuarioId()
                )
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @GetMapping("/campeonato/{id}/rodadas")
    public ResponseEntity<List<RodadaDTO>> gerarRodadas(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                partidaService.gerarRodadasPontosCorridos(id)
        );
    }
}

