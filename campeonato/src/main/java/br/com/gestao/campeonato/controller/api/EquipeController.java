package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.dto.EquipeResponseDTO;
import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<EquipeResponseDTO> cadastrar(@RequestBody Equipe equipe) {

        Equipe salva = equipeService.salvar(equipe);

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

    @PreAuthorize("permitAll()")
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

    @PreAuthorize("permitAll()")
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

    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        equipeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


