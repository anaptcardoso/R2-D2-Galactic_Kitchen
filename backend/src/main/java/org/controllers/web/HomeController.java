package org.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Serves the SPA's index.html for any non-API route
    // The JavaScript frontend handles routing from here
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Redirects SPA routes to index.html
    // Prevents 404 errors when the user directly accesses /recipes, /plan, etc.
    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

}
