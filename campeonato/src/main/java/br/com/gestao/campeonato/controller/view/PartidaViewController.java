package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.entity.SetPartida;
import br.com.gestao.campeonato.entity.Usuario;
import br.com.gestao.campeonato.repository.UsuarioRepository;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.EquipeService;
import br.com.gestao.campeonato.service.PartidaService;
import br.com.gestao.campeonato.service.SetPartidaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/partidas")
public class PartidaViewController {

    private final PartidaService partidaService;
    private final EquipeService equipeService;
    private final CampeonatoService campeonatoService;
    private final UsuarioRepository usuarioRepository;
    private final SetPartidaService setPartidaService;

    public PartidaViewController(PartidaService partidaService, EquipeService equipeService,
                                    CampeonatoService campeonatoService, UsuarioRepository usuarioRepository
    ,SetPartidaService setPartidaService) {
        this.partidaService = partidaService;
        this.equipeService = equipeService;
        this.campeonatoService = campeonatoService;
        this.usuarioRepository = usuarioRepository;
        this.setPartidaService = setPartidaService;
    }

    // LISTA
    @GetMapping("/campeonatos/{id}/partidas")
    public String listarPorCampeonato(@PathVariable Integer id,
                                      Model model) {

        model.addAttribute("partidas",
                partidaService.buscarPorCampeonato(id));

        model.addAttribute("campeonatoId", id);

        return "partidas/lista";
    }
    @GetMapping("/campeonato/{id}/nova")
    public String novaPartida(@PathVariable Integer id, Model model) {

        model.addAttribute("partida", new Partida());

        model.addAttribute("campeonato",
                campeonatoService.buscarPorId(id));

        model.addAttribute("equipes",
                equipeService.listarPorCampeonato(id));

        return "partidas/form";
    }
    @GetMapping("/resultado/{id}")
    public String resultado(@PathVariable Integer id, Model model) {
        Partida partida = partidaService.buscarPorId(id);
        model.addAttribute("partida", partida);
        return "partidas/resultado";
    }
    @GetMapping("/finalizar/{id}")
    public String finalizar(@PathVariable Integer id) {
        Partida partida = partidaService.buscarPorId(id);
        partida.setFinalizada(true);
        partidaService.salvar(partida);
        return "redirect:/partidas";
    }


    // TELA NOVA PARTIDA
    @GetMapping("/nova/{id}")
    public String nova(@PathVariable Integer id,
                       Model model) {

        model.addAttribute("equipes",
                equipeService.listarPorCampeonato(id));

        model.addAttribute("campeonatoId", id);

        return "partidas/form";
    }
    // SALVAR
    @PostMapping
    public String salvar(Partida partida){
        partidaService.salvar(partida);
        return "redirect:/campeonatos/"
                + partida.getCampeonato().getId()
                + "/partidas";
    }
    @GetMapping("/partidas/{id}/resultado")
    public String telaResultado(@PathVariable Integer id, Model model) {
        model.addAttribute("partida", partidaService.buscarPorId(id));
        return "partidas/resultado-form";
    }
    @PostMapping("/partidas/{id}/resultado")
    public String salvarResultado(@PathVariable Integer id,
                                  @RequestParam String resultado) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        partidaService.atualizarResultadoManual(id, resultado, usuario.getId());

        return "redirect:/partidas";
    }

    @GetMapping("/campeonatos/{id}/gerar-partidas")
    public String gerarPartidas(@PathVariable Integer id,
                                Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        partidaService.gerarPartidasPontosCorridos(
                id,
                authentication.getName()
        );

        return "redirect:/campeonatos/" + id + "/rodadas";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         Authentication authentication) {

        Partida partida = partidaService.buscarPorId(id);

        if (partida.getCampeonato().getIniciado()) {
            return "redirect:/partidas";
        }

        model.addAttribute("partida", partida);

        return "partidas/form";
    }
    @GetMapping("/{id}")
    public String detalhe(@PathVariable Integer id, Model model) {

        Partida partida = partidaService.buscarPorId(id);
        List<SetPartida> sets = setPartidaService.listarSets(id);

        model.addAttribute("partida", partida);
        model.addAttribute("sets", sets);

        return "partidas/detalhe";
    }
    @PostMapping("/sets/{id}")
    public String adicionarSet(@PathVariable Integer id,
                               @RequestParam Integer pontosMandante,
                               @RequestParam Integer pontosVisitante) {

        SetPartida set = new SetPartida();
        set.setPontosMandante(pontosMandante);
        set.setPontosVisitante(pontosVisitante);

        setPartidaService.criarSet(id, set);

        return "redirect:/partidas/" + id;
    }

}



