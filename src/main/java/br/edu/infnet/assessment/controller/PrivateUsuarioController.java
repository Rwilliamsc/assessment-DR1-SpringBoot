package br.edu.infnet.assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/usuarios")
public class PrivateUsuarioController {

    @GetMapping
    public String testeDePrivacidade (){
        return "Olá você está agora em um acesso privado!";
    }
}
