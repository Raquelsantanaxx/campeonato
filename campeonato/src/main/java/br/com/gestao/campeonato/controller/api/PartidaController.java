package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.dto.AtualizarResultadoRequest;
import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/partida")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }


    @PostMapping
    public ResponseEntity<Partida> cadastrar(@RequestBody Partida partida) {
        Partida salva = partidaService.salvar(partida);
        return ResponseEntity.ok(salva);
    }


    @GetMapping
    public ResponseEntity<List<Partida>> listarTodas() {
        return ResponseEntity.ok(partidaService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPorId(@PathVariable Integer id) {
        Partida partida = partidaService.buscarPorId(id);
        return ResponseEntity.ok(partida);
    }




    @GetMapping("/campeonato/{idCampeonato}")
    public ResponseEntity<List<Partida>> buscarPorCampeonato(@PathVariable Integer idCampeonato) {
        return ResponseEntity.ok(partidaService.buscarPorCampeonato(idCampeonato));
    }


    @GetMapping("/equipe/{idEquipe}")
    public ResponseEntity<List<Partida>> buscarPorEquipe(@PathVariable Integer idEquipe) {
        return ResponseEntity.ok(partidaService.buscarPorEquipe(idEquipe));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        partidaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/resultado")
    public ResponseEntity<Partida> atualizarResultado(
            @PathVariable Integer id,
            @RequestBody @Valid AtualizarResultadoRequest request) {

        Partida partida = partidaService.atualizarResultadoManual(
                id,
                request.getNovoResultado(),
                request.getUsuarioId()
        );

        return ResponseEntity.ok(partida);
    }
}

