/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 *
 * @author macuser
 */
public class Utilitario {

    private static Utilitario utilitario = null;

    public static final Utilitario getInstance() {
        if (utilitario == null) {
            utilitario = new Utilitario();
        }
        return utilitario;
    }

    public String formatacaoIene(long valor) {

        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.JAPAN);
        String precoFormatado = n.format(valor);

        return precoFormatado.substring(1);
    }

    public String formatacaoIeneNegativo(long valor) {

        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.JAPAN);
        String precoFormatado = n.format(valor);

        return precoFormatado;
    }

    public long diferencaDiasEntreData(String dataInicio, String dataFinal) {
        LocalDate dataInicioF = LocalDate.parse(dataInicio);
        LocalDate dataFinalF = LocalDate.parse(dataFinal);

        long diferencaEmDias = ChronoUnit.DAYS.between(dataInicioF, dataFinalF);

        return diferencaEmDias;
    }

    public String telefoneFormatado(String telefone, String tipoTelefone) {
        String telefoneFinal = "";

        if (tipoTelefone.equals("celular")) {
            telefoneFinal = telefone.replace("-", "").replace(" ", "");
            if (telefoneFinal.length() > 11) {
                telefoneFinal = telefoneFinal.substring(3, telefoneFinal.length());
            }
        } else {
            telefoneFinal = telefone.replace("-", "").replace(" ", "");
            if (telefoneFinal.length() > 10) {
                telefoneFinal = telefoneFinal.substring(3, telefoneFinal.length());
            }
        }

        return telefoneFinal;

    }

    public LocalDate obterPrimeiroDiaMes(int mes, int ano) {
        return LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate obterUltimoDiaMes(int mes, int ano) {
        return LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth());
    }

    public String pageGenerate(int offset, int qtdTotal, int length) {
        int MAX_PAGE_INDEX = 20;
        if (qtdTotal > length) {
            String header = "Paginas: ";
            int start = 0;

            for (int i = start; i < qtdTotal && i < start + MAX_PAGE_INDEX * length; i += length) {
                if (i == offset) {
                    header += "&nbsp;&nbsp;<b>" + (i / length + 1) + "</b>";
                } else {
                    header += "&nbsp;&nbsp;<a href=\"javascript:fPesquisa(" + i + ")\">" + (i / length + 1) + "</a>";
                }
            }
            return header;

        } else {
            return "";
        }
    }

}
