package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.dto.RodadaDTO;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RodadaViewController {

    private final PartidaService partidaService;

    public RodadaViewController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @GetMapping("/campeonatos/{id}/rodadas")
    public String verRodadas(@PathVariable Integer id, Model model) {

        List<RodadaDTO> rodadas = partidaService.gerarRodadasPontosCorridos(id);

        model.addAttribute("rodadas", rodadas);
        model.addAttribute("campeonatoId", id);

        return "rodadas/lista";
    }
}
