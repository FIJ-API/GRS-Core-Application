package com.grs.api.grs_api.dto;

import java.time.LocalDate;

public class BodyCamResponseDto {
    private String modelo;

    private String numeroDeSerie;

    private boolean chip;

    private String estado;

    private String vendedor;

    private String revenda;

    private LocalDate saida;

    private LocalDate devolucao;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public boolean isChip() {
        return chip;
    }

    public void setChip(boolean chip) {
        this.chip = chip;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getRevenda() {
        return revenda;
    }

    public void setRevenda(String revenda) {
        this.revenda = revenda;
    }

    public LocalDate getSaida() {
        return saida;
    }

    public void setSaida(LocalDate saida) {
        this.saida = saida;
    }

    public LocalDate getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Integer diasAVencer) {
        this.devolucao = saida.plusDays(diasAVencer);
    }
}
