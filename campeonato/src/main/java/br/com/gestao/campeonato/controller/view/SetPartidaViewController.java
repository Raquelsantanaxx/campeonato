package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.SetPartida;
import br.com.gestao.campeonato.service.SetPartidaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sets")
public class SetPartidaViewController {

    private final SetPartidaService service;

    public SetPartidaViewController(SetPartidaService service) {
        this.service = service;
    }

    @PostMapping("/{partidaId}")
    public String adicionarSet(@PathVariable Integer partidaId,
                               @RequestParam Integer pontosMandante,
                               @RequestParam Integer pontosVisitante,
                               RedirectAttributes redirectAttributes) {

        try {

            SetPartida set = new SetPartida();
            set.setPontosMandante(pontosMandante);
            set.setPontosVisitante(pontosVisitante);

            service.criarSet(partidaId, set);

            redirectAttributes.addFlashAttribute("sucesso",
                    "Set adicionado com sucesso!");

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute("erro",
                    e.getMessage());
        }

        return "redirect:/partidas/" + partidaId;
    }
}