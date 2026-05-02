package org.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Serve o index.html do SPA para qualquer rota não-API
    // O JavaScript (frontend) trata do routing a partir daqui
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Redireciona rotas do SPA para o index.html
    // Evita 404 quando o utilizador acede diretamente a /recipes, /plan, etc.
    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

}
