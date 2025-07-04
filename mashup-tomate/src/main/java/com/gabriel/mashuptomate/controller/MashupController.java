package com.gabriel.mashuptomate.controller;

import com.gabriel.mashuptomate.dto.MashupRequest;
import com.gabriel.mashuptomate.dto.MashupResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/venda")
public class MashupController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public MashupResponse calcular(@RequestBody MashupRequest request) {
        int quantidade = request.getQuantidade();
        int distancia = request.getDistancia();

        // Chamada REST para preco-tomate
        Map<String, Integer> precoRequest = Map.of("quantidade", quantidade);
        @SuppressWarnings("unchecked")
        ResponseEntity<Map<String, Object>> precoResponse = (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>)
                restTemplate.postForEntity(
                        "http://localhost:8080/preco",
                        precoRequest,
                        Map.class
                );
        double precoTomate = ((Number) precoResponse.getBody().get("precoFinal")).doubleValue();

        // Chamada GraphQL para frete-tomate
        String query = "{ frete(quantidade: " + quantidade + ", distancia: " + distancia + ") { precoFrete veiculo } }";
        Map<String, String> graphql = Map.of("query", query);
        @SuppressWarnings("unchecked")
        ResponseEntity<Map<String, Object>> freteResponse = (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>)
                restTemplate.postForEntity(
                        "http://localhost:8081/graphql",
                        graphql,
                        Map.class
                );

        @SuppressWarnings("unchecked")
        Map<String, Object> freteData = (Map<String, Object>) ((Map<?, ?>) freteResponse.getBody().get("data")).get("frete");

        double frete = ((Number) freteData.get("precoFrete")).doubleValue();
        String veiculo = (String) freteData.get("veiculo");

        double valorBruto = precoTomate + frete;
        double lucro = valorBruto * 0.55;

        double desconto = 0;
        if (quantidade > 300) {
            desconto = (valorBruto + lucro) * 0.12;
        } else if (quantidade > 50) {
            desconto = (valorBruto + lucro) * 0.075;
        }

        double totalComLucroEDesconto = (valorBruto + lucro - desconto);
        double valorComImpostos = totalComLucroEDesconto * 1.27;

        return new MashupResponse(precoTomate, frete, veiculo, valorBruto, lucro, desconto, valorComImpostos);
    }
}
