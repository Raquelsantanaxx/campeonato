package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/equipe")

public class EquipeController {
    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping
    public ResponseEntity<Equipe> cadastrar(@RequestBody Equipe equipe) {
        Equipe salva = equipeService.salvar(equipe);
        return ResponseEntity.ok(salva);
    }
    @GetMapping
    public ResponseEntity<List<Equipe>> listarTodos() {
        return ResponseEntity.ok(equipeService.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Equipe> buscarPorId(@PathVariable Integer id) {
        return equipeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        equipeService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}



