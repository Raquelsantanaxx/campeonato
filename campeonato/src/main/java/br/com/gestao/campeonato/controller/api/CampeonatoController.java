package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;
    private final PartidaService partidaService;
    public CampeonatoController(CampeonatoService campeonatoService, PartidaService partidaService) {
        this.campeonatoService = campeonatoService;
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<Campeonato> salvar(@RequestBody Campeonato campeonato) {
        Campeonato salvo = campeonatoService.salvar(campeonato);
        return ResponseEntity.ok(salvo);
    }



    @GetMapping
    public ResponseEntity<List<Campeonato>> listarTodos() {
        return ResponseEntity.ok(campeonatoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> buscarPorId(@PathVariable Integer id) {
        Campeonato campeonato = campeonatoService.buscarPorId(id);
        return ResponseEntity.ok(campeonato);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Integer id) {
        campeonatoService.desativar(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciar(@PathVariable Integer id) {
        campeonatoService.iniciarCampeonato(id);
        return ResponseEntity.ok().build();
    }


}



