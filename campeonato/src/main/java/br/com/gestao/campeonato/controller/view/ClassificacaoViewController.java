package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.service.ClassificacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/campeonatos")
public class ClassificacaoViewController {

    private final ClassificacaoService classificacaoService;

    public ClassificacaoViewController(ClassificacaoService classificacaoService) {
        this.classificacaoService = classificacaoService;
    }

    @GetMapping("/{id}/classificacao")
    public String verClassificacao(@PathVariable Integer id, Model model) {

        var tabela = classificacaoService.gerarClassificacao(id);

        model.addAttribute("tabela", tabela);
        model.addAttribute("campeonatoId", id);

        return "classificacao/lista";
    }
}
