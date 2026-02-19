package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.entity.SetPartida;
import br.com.gestao.campeonato.service.SetPartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/set_partida")
public class SetPartidaController {

    private final SetPartidaService setService;

    public SetPartidaController(SetPartidaService setService) {
        this.setService = setService;
    }

    @PostMapping("/partida/{partidaId}")
    public ResponseEntity<SetPartida> criarSet(
            @PathVariable Integer partidaId,
            @RequestBody SetPartida set) {

        SetPartida salvo = setService.criarSet(partidaId, set);
        return ResponseEntity.ok(salvo);
    }


    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<SetPartida>> listarSets(
            @PathVariable Integer partidaId) {

        return ResponseEntity.ok(setService.listarSets(partidaId));
    }
}

