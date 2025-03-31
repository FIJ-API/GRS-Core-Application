package com.grs.api.grs_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class BodyCam {

    @Id
    private int idBodyCam;

    @NotBlank
    private String modelo;

    private String numeroDeSerie;

    private boolean chip;

    private String estado;

    private String vendedor;

    private String revenda;

    private LocalDate saida;

    private int diasAVencer;

    public int getIdBodyCam() {
        return idBodyCam;
    }

    public void setIdBodyCam(int idBodyCam) {
        this.idBodyCam = idBodyCam;
    }

    public @NotBlank String getModelo() {
        return modelo;
    }

    public void setModelo(@NotBlank String modelo) {
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

    public void setSaida() {
        this.saida = LocalDate.now();
    }

    public int getDiasAVencer() {
        return diasAVencer;
    }

    public void setDiasAVencer(int diasAVencer) {
        this.diasAVencer = diasAVencer;
    }
}
