package br.com.gestao.campeonato.controller.view;

import br.com.gestao.campeonato.entity.PerfilUsuario;
import br.com.gestao.campeonato.entity.Usuario;
import br.com.gestao.campeonato.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastro")
@RequiredArgsConstructor
public class CadastroViewController {

    private final UsuarioService usuarioService;

    // 🔹 ABRE A TELA DE CADASTRO
    @GetMapping
    public String cadastroForm(Model model) {

        // cria objeto vazio para o formulário
        model.addAttribute("usuario", new Usuario());

        // caminho correto da view
        return "cadastro/cadastro";
    }

    // 🔹 PROCESSA O CADASTRO
    @PostMapping
    public String cadastrar(
            @ModelAttribute("usuario") Usuario usuario,
            Model model) {

        // perfil padrão do sistema
        usuario.setPerfil(PerfilUsuario.ORGANIZADOR);

        try {
            usuarioService.salvar(usuario);

            // padrão POST-REDIRECT-GET
            return "redirect:/login";

        } catch (IllegalArgumentException e) {

            // envia mensagem de erro para tela
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usuario", usuario);

            return "cadastro/cadastro";
        }
    }
}
