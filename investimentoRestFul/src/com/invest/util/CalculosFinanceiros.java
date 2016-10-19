package com.invest.util;

public class CalculosFinanceiros {
    public static Double calcularJurosCompostosJuros(Double principal, Double taxa, int meses) {
        double juros = CalculosFinanceiros.calcularJurosCompostosMontante(principal, taxa, meses) - principal;
        return juros;
    }

    public static Double calcularJurosCompostosMontante(Double principal, Double taxa, int meses) {
        double montante = principal * Math.pow(1.0 + taxa / 12.0, meses);
        return montante;
    }

    public static Double calcularVariacaoEntreValores(Double valor1, Double valor2) {
        if (valor1 > 0.0) {
            return valor2 * 100.0 / valor1 / 100.0;
        }
        return 0.0;
    }
}