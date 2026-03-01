package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.entity.Jogador;
import br.com.gestao.campeonato.service.JogadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }


    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<Jogador> salvar(@RequestBody Jogador jogador) {
        Jogador salvo = jogadorService.salvar(jogador);
        return ResponseEntity.ok(salvo);
    }


    @GetMapping
    public ResponseEntity<List<Jogador>> listarTodos() {
        return ResponseEntity.ok(jogadorService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarPorId(@PathVariable Integer id) {
        return jogadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/equipe/{idEquipe}")
    public ResponseEntity<List<Jogador>> listarPorEquipe(
            @PathVariable Integer idEquipe) {
        return ResponseEntity.ok(jogadorService.listarPorEquipe(idEquipe));
    }


    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        jogadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}