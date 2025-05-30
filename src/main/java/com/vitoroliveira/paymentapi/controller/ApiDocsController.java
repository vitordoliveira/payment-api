package com.vitoroliveira.paymentapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApiDocsController {

    @GetMapping("/api-documentation")
    public RedirectView apiDocsRedirect() {
        // Redirecionamos para a URL alternativa do Swagger UI
        return new RedirectView("/swagger-ui/index.html");
    }
    
    // Opcional: Verificação de saúde da API para um endpoint que sabemos que deve funcionar
    @GetMapping("/api/health")
    @ResponseBody
    public String apiHealth() {
        return "{\"status\":\"UP\", \"swagger\":\"Você pode estar enfrentando problemas com o Swagger UI. " +
               "Tente acessar /v3/api-docs para obter os dados brutos da API no formato JSON.\"}";
    }
}