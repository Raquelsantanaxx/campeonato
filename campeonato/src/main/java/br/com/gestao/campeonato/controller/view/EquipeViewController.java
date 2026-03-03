package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.service.CampeonatoService;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // ===============================
    // LISTAR
    // ===============================
    @GetMapping
    public String listar(Model model, Authentication authentication) {

        model.addAttribute("equipes", equipeService.listarTodos());

        if (authentication != null) {
            model.addAttribute("usuarioLogado", authentication.getName());
        }

        return "equipes/lista";
    }

    // ===============================
    // NOVA
    // ===============================
    @GetMapping("/novo/{id}")
    public String novo(@PathVariable Integer id,
                       Model model,
                       Authentication authentication) {

        var campeonato = campeonatoService.buscarPorId(id);

        // Segurança: só organizador pode cadastrar equipe
        if (authentication == null ||
                !campeonato.getOrganizador()
                        .getEmail()
                        .equals(authentication.getName())) {

            return "redirect:/equipes/campeonato/" + id;
        }

        Equipe equipe = new Equipe();
        equipe.setCampeonato(campeonato); // ESSENCIAL

        model.addAttribute("equipe", equipe);
        model.addAttribute("campeonatos", campeonatoService.listarTodos());
        model.addAttribute("campeonato", campeonato);

        return "equipes/form";
    }

    // ===============================
    // SALVAR
    // ===============================
    @PostMapping
    public String salvar(Equipe equipe,
                         Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        equipeService.salvar(equipe, authentication.getName());

        return "redirect:/equipes";
    }

    // ===============================
    // EDITAR
    // ===============================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         Authentication authentication) {

        Equipe equipe = equipeService.buscarPorId(id);

        if (authentication == null ||
                !equipe.getCampeonato()
                        .getOrganizador()
                        .getEmail()
                        .equals(authentication.getName())) {

            return "redirect:/equipes";
        }

        model.addAttribute("equipe", equipe);
        model.addAttribute("campeonatos", campeonatoService.listarTodos());

        return "equipes/form";
    }

    // ===============================
    // EXCLUIR
    // ===============================
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {

        try {
            equipeService.deletar(id, authentication.getName());
            redirectAttributes.addFlashAttribute("sucesso",
                    "Equipe excluída com sucesso.");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro",
                    e.getMessage());
        }

        return "redirect:/equipes";
    }
    @GetMapping("/campeonato/{id}")
    public String listarPorCampeonato(@PathVariable Integer id,
                                      Model model,
                                      Authentication authentication) {

        // BUSCA O CAMPEONATO
        var campeonato = campeonatoService.buscarPorId(id);

        // BUSCA EQUIPES
        var equipes = equipeService.listarPorCampeonato(id);

        model.addAttribute("equipes", equipes);
        model.addAttribute("campeonato", campeonato);

        if (authentication != null) {
            model.addAttribute("usuarioLogado", authentication.getName());
        }

        return "equipes/lista";
    }
}



