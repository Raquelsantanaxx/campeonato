package br.com.gestao.campeonato.controller.api;

import br.com.gestao.campeonato.dto.ClassificacaoEquipeDTO;
import br.com.gestao.campeonato.service.ClassificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classificacao")
public class ClassificacaoController {

    private final ClassificacaoService classificacaoService;

    public ClassificacaoController(ClassificacaoService classificacaoService) {
        this.classificacaoService = classificacaoService;
    }

    @GetMapping("/{campeonatoId}")
    public ResponseEntity<List<ClassificacaoEquipeDTO>> gerar(
            @PathVariable Integer campeonatoId) {

        List<ClassificacaoEquipeDTO> tabela =
                classificacaoService.gerarClassificacao(campeonatoId);

        return ResponseEntity.ok(tabela);
    }
}

