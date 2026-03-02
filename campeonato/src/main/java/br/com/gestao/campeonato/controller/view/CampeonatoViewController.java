package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/campeonatos")
public class CampeonatoViewController {

    private final CampeonatoService campeonatoService;
    private final PartidaService partidaService;

    public CampeonatoViewController(CampeonatoService campeonatoService, PartidaService partidaService) {
        this.campeonatoService = campeonatoService;
        this.partidaService = partidaService;
    }

    // ===============================
    // LISTAR CAMPEONATOS
    // ===============================
    @GetMapping
    public String listar(Model model, Authentication authentication) {

        model.addAttribute("campeonatos", campeonatoService.listarTodos());

        if (authentication != null) {
            model.addAttribute("usuarioLogado", authentication.getName());
        }

        return "campeonatos/lista";
    }

    // ===============================
    // ABRIR FORMULÁRIO NOVO CAMPEONATO
    // ===============================
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("campeonato", new Campeonato());
        return "campeonatos/form";
    }

    // ===============================
    // SALVAR (CRIAR OU ATUALIZAR)
    // ===============================
    @PostMapping
    @PreAuthorize("hasRole('ORGANIZADOR')")
    public String salvar(@ModelAttribute Campeonato campeonato,
                         Authentication authentication,
                         Model model) {

        if (authentication == null) {
            return "redirect:/login";
        }

        String email = authentication.getName();

        try {

            campeonatoService.salvar(campeonato, email);
            return "redirect:/campeonatos";

        } catch (RuntimeException e) {

            model.addAttribute("erro", e.getMessage());
            model.addAttribute("campeonato", campeonato);

            return "campeonatos/form";
            // ⚠ coloque exatamente o caminho do seu form de campeonato
        }
    }


    // ===============================
    // ABRIR FORMULÁRIO PARA EDITAR
    // ===============================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         Authentication authentication) {

        Campeonato campeonato = campeonatoService.buscarPorId(id);

        // 🔒 Segurança: só dono pode editar
        if (!campeonato.getOrganizador()
                .getEmail()
                .equals(authentication.getName())) {

            return "redirect:/campeonatos";
        }

        model.addAttribute("campeonato", campeonato);
        return "campeonatos/form";
    }

    // ===============================
    // DESATIVAR CAMPEONATO
    // ===============================
    @PreAuthorize("hasRole('ORGANIZADOR')")
    @PostMapping("/desativar/{id}")
    public String desativar(@PathVariable Integer id,
                            Authentication authentication) {

        campeonatoService.desativar(id, authentication.getName());

        return "redirect:/campeonatos";
    }
    // 📍 CampeonatoViewController (VIEW CONTROLLER)

    @PostMapping("/{id}/gerar-partidas")
    @PreAuthorize("hasRole('ORGANIZADOR')")
    public String gerarPartidas(@PathVariable Integer id,
                                Authentication authentication,
                                Model model) {

        try {

            partidaService.gerarPartidasPontosCorridos(
                    id,
                    authentication.getName()
            );

            return "redirect:/campeonatos/" + id;

        } catch (RuntimeException e) {

            Campeonato campeonato = campeonatoService.buscarPorId(id);

            model.addAttribute("campeonato", campeonato);
            model.addAttribute("usuarioLogado", authentication.getName());
            model.addAttribute("erro", e.getMessage());

            return "campeonatos/detalhe";
        }
    }
    @PostMapping("/{id}/iniciar")
    @PreAuthorize("hasRole('ORGANIZADOR')")
    public String iniciar(@PathVariable Integer id,
                          Authentication authentication,
                          Model model) {

        try {

            campeonatoService.iniciarCampeonato(
                    id,
                    authentication.getName()
            );

            return "redirect:/campeonatos/" + id;

        } catch (RuntimeException e) {

            Campeonato campeonato = campeonatoService.buscarPorId(id);

            model.addAttribute("campeonato", campeonato);
            model.addAttribute("usuarioLogado", authentication.getName());
            model.addAttribute("erro", e.getMessage());

            return "campeonatos/detalhe";
        }
    }
    @GetMapping("/{id}")
    public String detalhe(@PathVariable Integer id,
                           Model model,
                           Authentication authentication) {

        var campeonato = campeonatoService.buscarPorId(id);

        model.addAttribute("campeonato", campeonato);

        if (authentication != null) {
            model.addAttribute("usuarioLogado", authentication.getName());
        }

        return "campeonatos/detalhe";
    }




}

