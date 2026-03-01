package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.service.CampeonatoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/campeonatos")
public class CampeonatoViewController {

    private final CampeonatoService campeonatoService;

    public CampeonatoViewController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    // ===============================
    // LISTAR CAMPEONATOS
    // ===============================
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("campeonatos", campeonatoService.listarTodos());
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
                         Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        String email = authentication.getName();

        campeonatoService.salvar(campeonato, email);

        return "redirect:/campeonatos";
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
}

