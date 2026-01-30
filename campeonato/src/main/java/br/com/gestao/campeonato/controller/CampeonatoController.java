package br.com.gestao.campeonato.controller;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.service.CampeonatoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    public CampeonatoController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
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

}



