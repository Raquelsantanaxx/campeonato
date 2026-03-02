package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.entity.Jogador;
import br.com.gestao.campeonato.service.EquipeService;
import br.com.gestao.campeonato.service.JogadorService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // ==========================================
    // LISTAR TODOS
    // ==========================================
    @GetMapping
    public String listar(Model model, Authentication authentication) {

        try {
            model.addAttribute("jogadores", jogadorService.listarTodos());
        } catch (Exception e) {
            model.addAttribute("jogadores", java.util.Collections.emptyList());
        }

        if (authentication != null) {
            model.addAttribute("usuarioLogado", authentication.getName());
        }

        return "jogadores/lista";
    }

    // ==========================================
    // LISTAR POR EQUIPE
    // ==========================================
    @GetMapping("/equipe/{id}")
    public String listarPorEquipe(@PathVariable Integer id,
                                  Model model,
                                  Authentication authentication) {

        Equipe equipe;

        try {
            equipe = equipeService.buscarPorId(id);
        } catch (Exception e) {
            return "redirect:/equipes";
        }

        model.addAttribute("jogadores",
                jogadorService.listarPorEquipe(id));

        model.addAttribute("equipe", equipe);

        if (authentication != null) {
            model.addAttribute("usuarioLogado", authentication.getName());
        }

        return "jogadores/lista";
    }

    // ==========================================
    // FORM NOVO JOGADOR
    // ==========================================
    @GetMapping("/novo/{idEquipe}")
    public String novoJogador(@PathVariable Integer idEquipe,
                              Model model) {

        Equipe equipe;

        try {
            equipe = equipeService.buscarPorId(idEquipe);
        } catch (Exception e) {
            return "redirect:/equipes";
        }

        Jogador jogador = new Jogador();
        jogador.setEquipe(equipe);

        model.addAttribute("jogador", jogador);
        model.addAttribute("equipes", equipeService.listarTodos());

        return "jogadores/form";
    }

    // ==========================================
    // EDITAR
    // ==========================================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         Authentication authentication) {

        Jogador jogador = jogadorService.buscarPorId(id);

        // 🔐 Segurança
        if (authentication == null ||
                !jogador.getEquipe()
                        .getCampeonato()
                        .getOrganizador()
                        .getEmail()
                        .equals(authentication.getName())) {

            return "redirect:/equipes";
        }

        model.addAttribute("jogador", jogador);

        // 🔥 ESSA LINHA ESTAVA FALTANDO
        model.addAttribute("equipes", equipeService.listarTodos());

        return "jogadores/form";
    }

    // ==========================================
    // SALVAR
    // ==========================================
    @PostMapping
    public String salvar(Jogador jogador,
                         Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        Jogador salvo = jogadorService.salvar(
                jogador,
                authentication.getName()
        );

        return "redirect:/jogadores/equipe/" +
                salvo.getEquipe().getId();
    }

    // ==========================================
    // EXCLUIR
    // ==========================================
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id,
                          Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        Jogador jogador = jogadorService.buscarPorId(id);

        // 🔐 segurança
        if (!jogador.getEquipe()
                .getCampeonato()
                .getOrganizador()
                .getEmail()
                .equals(authentication.getName())) {

            return "redirect:/equipes";
        }

        Integer idEquipe = jogador.getEquipe().getId();

        jogadorService.deletar(id, authentication.getName());

        return "redirect:/jogadores/equipe/" + idEquipe;
    }
    @GetMapping("/novo")
    public String novoJogador(Model model) {

        Jogador jogador = new Jogador();

        // 🔥 IMPORTANTE
        jogador.setEquipe(new Equipe());

        model.addAttribute("jogador", jogador);
        model.addAttribute("equipes", equipeService.listarTodos());

        return "jogadores/form";
    }
}

