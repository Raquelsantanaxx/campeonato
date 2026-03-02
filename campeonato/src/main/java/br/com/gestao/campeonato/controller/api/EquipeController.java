package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.dto.EquipeResponseDTO;
import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    // ===============================
    // CADASTRAR EQUIPE
    // ===============================
    @PreAuthorize("hasRole('ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<EquipeResponseDTO> cadastrar(
            @RequestBody Equipe equipe,
            Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        Equipe salva = equipeService.salvar(
                equipe,
                authentication.getName()
        );

        return ResponseEntity.ok(
                new EquipeResponseDTO(
                        salva.getId(),
                        salva.getNome(),
                        salva.getSigla(),
                        salva.getCampeonato().getId(),
                        salva.getCampeonato().getNome()
                )
        );
    }

    // ===============================
    // LISTAR TODAS (PÚBLICO)
    // ===============================
    @GetMapping
    public ResponseEntity<List<EquipeResponseDTO>> listarTodos() {

        List<EquipeResponseDTO> lista = equipeService.listarTodos()
                .stream()
                .map(e -> new EquipeResponseDTO(
                        e.getId(),
                        e.getNome(),
                        e.getSigla(),
                        e.getCampeonato().getId(),
                        e.getCampeonato().getNome()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }

    // ===============================
    // LISTAR POR CAMPEONATO (PÚBLICO)
    // ===============================
    @GetMapping("/campeonato/{campeonatoId}")
    public ResponseEntity<List<EquipeResponseDTO>> listarPorCampeonato(
            @PathVariable Integer campeonatoId) {

        List<EquipeResponseDTO> lista = equipeService
                .listarPorCampeonato(campeonatoId)
                .stream()
                .map(e -> new EquipeResponseDTO(
                        e.getId(),
                        e.getNome(),
                        e.getSigla(),
                        e.getCampeonato().getId(),
                        e.getCampeonato().getNome()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }

    // ===============================
    // DELETAR EQUIPE
    // ===============================
    @PreAuthorize("hasRole('ORGANIZADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id,
            Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        equipeService.deletar(
                id,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }
}


