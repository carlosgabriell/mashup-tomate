package com.gabriel.mashuptomate.dto;

public class MashupResponse {
    public double precoTomate;
    public double frete;
    public String veiculo;
    public double valorBruto;
    public double lucro;
    public double desconto;
    public double valorComImpostos;

    public MashupResponse(double precoTomate, double frete, String veiculo, double valorBruto,
                          double lucro, double desconto, double valorComImpostos) {
        this.precoTomate = precoTomate;
        this.frete = frete;
        this.veiculo = veiculo;
        this.valorBruto = valorBruto;
        this.lucro = lucro;
        this.desconto = desconto;
        this.valorComImpostos = valorComImpostos;
    }
}