package br.com.gestao.campeonato.controller;

import br.com.gestao.campeonato.entity.CriterioDeDesempate;
import br.com.gestao.campeonato.service.CriterioDeDesempateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/criterio_desempate")
public class CriterioDeDesempateController {

    private final CriterioDeDesempateService criterioService;

    public CriterioDeDesempateController(CriterioDeDesempateService criterioService) {
        this.criterioService = criterioService;
    }


    @PostMapping
    public ResponseEntity<CriterioDeDesempate> cadastrar(
            @RequestBody CriterioDeDesempate criterio) {

        return ResponseEntity.ok(criterioService.salvar(criterio));
    }

    @GetMapping
    public ResponseEntity<List<CriterioDeDesempate>> listarTodos() {
        return ResponseEntity.ok(criterioService.listarTodos());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        criterioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

