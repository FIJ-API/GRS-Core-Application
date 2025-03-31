package com.grs.api.grs_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BodyCamRequestDto {
    @NotBlank
    private String modelo;

    @NotBlank
    private String numeroDeSerie;

    @NotNull
    private boolean chip;

    @NotBlank
    private String estado;

    @NotBlank
    private String vendedor;

    @NotBlank
    private String revenda;

    @DecimalMin(value= "1", message = "Dia para vencimento")
    private int diasAVencer;

    public @NotBlank String getModelo() {
        return modelo;
    }

    public void setModelo(@NotBlank String modelo) {
        this.modelo = modelo;
    }

    public @NotBlank String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(@NotBlank String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    @NotNull
    public boolean isChip() {
        return chip;
    }

    public void setChip(@NotNull boolean chip) {
        this.chip = chip;
    }

    public @NotBlank String getEstado() {
        return estado;
    }

    public void setEstado(@NotBlank String estado) {
        this.estado = estado;
    }

    public @NotBlank String getVendedor() {
        return vendedor;
    }

    public void setVendedor(@NotBlank String vendedor) {
        this.vendedor = vendedor;
    }

    public @NotBlank String getRevenda() {
        return revenda;
    }

    public void setRevenda(@NotBlank String revenda) {
        this.revenda = revenda;
    }

    @DecimalMin(value = "1", message = "Dia para vencimento")
    public int getDiasAVencer() {
        return diasAVencer;
    }

    public void setDiasAVencer(@DecimalMin(value = "1", message = "Dia para vencimento") int diasAVencer) {
        this.diasAVencer = diasAVencer;
    }


}
