package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Jogador;
import br.com.gestao.campeonato.service.EquipeService;
import br.com.gestao.campeonato.service.JogadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jogadores")
public class JogadorViewController {

    private final JogadorService jogadorService;
    private final EquipeService equipeService;

    public JogadorViewController(JogadorService jogadorService,
                                 EquipeService equipeService) {
        this.jogadorService = jogadorService;
        this.equipeService = equipeService;
    }

    // LISTA
    @GetMapping
    public String listar(Model model) {

        model.addAttribute("jogadores", jogadorService.listarTodos());
        model.addAttribute("equipes", equipeService.listarTodos());

        return "jogadores/lista";
    }

    // FORM NOVO
    @GetMapping("/novo")
    public String novoJogador(Model model) {
        model.addAttribute("jogador", new Jogador());
        model.addAttribute("equipes", equipeService.listarTodos());
        return "jogadores/form";
    }


    // SALVAR
    @PostMapping("/salvar")
    public String salvar(Jogador jogador) {

        jogadorService.salvar(jogador);
        return "redirect:/jogadores";
    }
}

