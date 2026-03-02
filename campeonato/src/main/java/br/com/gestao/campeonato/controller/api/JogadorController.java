package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.entity.Jogador;
import br.com.gestao.campeonato.service.JogadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    // ===============================
    // SALVAR
    // ===============================
    @PreAuthorize("hasRole('ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<Jogador> salvar(
            @RequestBody Jogador jogador,
            Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        Jogador salvo = jogadorService.salvar(
                jogador,
                authentication.getName()
        );

        return ResponseEntity.ok(salvo);
    }

    // ===============================
    // BUSCAR POR ID
    // ===============================
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(jogadorService.buscarPorId(id));
    }

    // ===============================
    // LISTAR POR EQUIPE
    // ===============================
    @GetMapping("/equipe/{idEquipe}")
    public ResponseEntity<List<Jogador>> listarPorEquipe(
            @PathVariable Integer idEquipe) {

        return ResponseEntity.ok(
                jogadorService.listarPorEquipe(idEquipe)
        );
    }

    // ===============================
    // DELETAR
    // ===============================
}