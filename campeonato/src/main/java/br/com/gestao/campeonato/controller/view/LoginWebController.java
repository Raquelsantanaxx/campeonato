package br.com.gestao.campeonato.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // carrega login.html
    }
}



