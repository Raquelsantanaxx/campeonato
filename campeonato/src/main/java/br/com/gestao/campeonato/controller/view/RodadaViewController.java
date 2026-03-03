package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/campeonatos")
public class RodadaViewController {

    private final PartidaService partidaService;

    public RodadaViewController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @GetMapping("/{id}/rodadas")
    public String verRodadas(@PathVariable Integer id, Model model) {

        var rodadas = partidaService.gerarRodadasPontosCorridos(id);

        if (rodadas.isEmpty()) {
            model.addAttribute("mensagem",
                    "As rodadas serão exibidas após gerar as partidas.");
        }

        model.addAttribute("rodadas", rodadas);
        model.addAttribute("campeonatoId", id);

        return "rodadas/lista";
    }

}
