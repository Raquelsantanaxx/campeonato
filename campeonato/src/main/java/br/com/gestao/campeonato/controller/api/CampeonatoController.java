package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;
    private final PartidaService partidaService;

    public CampeonatoController(CampeonatoService campeonatoService,
                                PartidaService partidaService) {
        this.campeonatoService = campeonatoService;
        this.partidaService = partidaService;
    }

    // 🔒 Criar campeonato
    @PreAuthorize("hasRole('ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<Campeonato> salvar(@RequestBody Campeonato campeonato,
                                             Authentication authentication) {

        String emailUsuario = authentication.getName();

        Campeonato salvo =
                campeonatoService.salvar(campeonato, emailUsuario);

        return ResponseEntity.ok(salvo);
    }

    // 🌍 Público — listar campeonatos
    @GetMapping
    public ResponseEntity<List<Campeonato>> listarTodos() {
        return ResponseEntity.ok(campeonatoService.listarTodos());
    }

    // 🌍 Público — buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> buscarPorId(@PathVariable Integer id) {
        Campeonato campeonato = campeonatoService.buscarPorId(id);
        return ResponseEntity.ok(campeonato);
    }

    // 🔒 Atualizar campeonato (somente dono)
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> atualizar(@PathVariable Integer id,
                                                @RequestBody Campeonato campeonato,
                                                Authentication authentication) {

        Campeonato atualizado =
                campeonatoService.atualizar(id, campeonato, authentication.getName());

        return ResponseEntity.ok(atualizado);
    }

    // 🔒 Desativar campeonato (somente dono)
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Integer id,
                                          Authentication authentication) {

        campeonatoService.desativar(id, authentication.getName());

        return ResponseEntity.noContent().build();
    }

    // 🔒 Iniciar campeonato (somente dono)
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZADOR')")
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciar(@PathVariable Integer id,
                                        Authentication authentication) {

        campeonatoService.iniciarCampeonato(id, authentication.getName());

        return ResponseEntity.ok().build();
    }
}


