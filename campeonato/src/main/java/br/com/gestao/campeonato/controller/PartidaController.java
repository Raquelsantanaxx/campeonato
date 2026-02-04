package br.com.gestao.campeonato.controller;

import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return partidaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
}

