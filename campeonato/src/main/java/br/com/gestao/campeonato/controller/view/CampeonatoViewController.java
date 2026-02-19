package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.service.CampeonatoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/campeonatos")
public class CampeonatoViewController {

    private final CampeonatoService campeonatoService;

    public CampeonatoViewController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("campeonatos", campeonatoService.listarTodos());
        return "campeonatos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("campeonato", new Campeonato());
        return "campeonatos/form";
    }

    @PostMapping
    public String salvar(Campeonato campeonato){
        campeonatoService.salvar(campeonato);
        return "redirect:/campeonatos";
    }
}


