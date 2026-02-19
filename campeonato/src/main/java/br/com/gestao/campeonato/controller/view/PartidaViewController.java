package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.entity.Usuario;
import br.com.gestao.campeonato.repository.UsuarioRepository;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.EquipeService;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/partidas")
public class PartidaViewController {

    private final PartidaService partidaService;
    private final EquipeService equipeService;
    private final CampeonatoService campeonatoService;
    private final UsuarioRepository usuarioRepository;

    public PartidaViewController(PartidaService partidaService, EquipeService equipeService,
                                    CampeonatoService campeonatoService, UsuarioRepository usuarioRepository) {
        this.partidaService = partidaService;
        this.equipeService = equipeService;
        this.campeonatoService = campeonatoService;
        this.usuarioRepository = usuarioRepository;
    }

    // LISTA
    @GetMapping
    public String listar(Model model){
        model.addAttribute("partidas", partidaService.listarTodos());
        return "partidas/lista";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Partida partida = partidaService.buscarPorId(id);
        model.addAttribute("partida", partida);
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
    @GetMapping("/nova")
    public String nova(Model model){
        model.addAttribute("equipes", equipeService.listarTodos());
        model.addAttribute("campeonatos", campeonatoService.listarTodos());
        return "partidas/form";
    }
    // SALVAR
    @PostMapping
    public String salvar(Partida partida){
        partidaService.salvar(partida);
        return "redirect:/partidas";
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


}



