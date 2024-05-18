package fr.univrouen.cv24v1.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
    @GetMapping(path = "", produces = MediaType.TEXT_HTML_VALUE) // Page d'Acceuil
    public String getMainPage() {
        return "accueil";
    }

    @GetMapping(path = "/help", produces = MediaType.TEXT_HTML_VALUE) // Aide
    public String getHelp() {
        return "aide";
    }
}