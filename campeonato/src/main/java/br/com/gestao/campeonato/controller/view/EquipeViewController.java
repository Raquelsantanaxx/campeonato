package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipes")
public class EquipeViewController {

    private final EquipeService equipeService;
    private final CampeonatoService campeonatoService;

    public EquipeViewController(EquipeService equipeService,
                                CampeonatoService campeonatoService) {
        this.equipeService = equipeService;
        this.campeonatoService = campeonatoService;
    }

    // LISTA DE EQUIPES
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipes", equipeService.listarTodos());
        return "equipes/lista";
    }

    // FORMULÁRIO DE NOVA EQUIPE
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("equipe", new Equipe());
        model.addAttribute("campeonatos", campeonatoService.listarTodos());
        return "equipes/form";
    }

    // SALVAR EQUIPE
    @PostMapping
    public String salvar(Equipe equipe) {
        equipeService.salvar(equipe);
        return "redirect:/equipes";
    }
}



