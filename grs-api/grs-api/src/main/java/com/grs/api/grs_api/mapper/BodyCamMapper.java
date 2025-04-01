package com.grs.api.grs_api.mapper;

import com.grs.api.grs_api.dto.BodyCamRequestDto;
import com.grs.api.grs_api.dto.BodyCamResponseDto;
import com.grs.api.grs_api.entity.BodyCam;

public class BodyCamMapper {
    public static BodyCam toEntity(BodyCamRequestDto bodyCamRequestDto) {

        if (bodyCamRequestDto == null) return null;

        BodyCam bodyCam = new BodyCam();
        bodyCam.setModelo(bodyCamRequestDto.getModelo());
        bodyCam.setNumeroDeSerie(bodyCamRequestDto.getNumeroDeSerie());
        bodyCam.setChip(bodyCamRequestDto.isChip());
        bodyCam.setEstado(bodyCamRequestDto.getEstado());
        bodyCam.setVendedor(bodyCamRequestDto.getVendedor());
        bodyCam.setRevenda(bodyCamRequestDto.getRevenda());
        bodyCam.setSaida();
        bodyCam.setDiasAVencer(bodyCamRequestDto.getDiasAVencer());
        return bodyCam;
    }

    public static BodyCamResponseDto toDto(BodyCam bodyCam) {

        if (bodyCam == null) return null;

        BodyCamResponseDto bodyCamResponseDto = new BodyCamResponseDto();
        bodyCamResponseDto.setIdBodyCam(bodyCam.getIdBodyCam());
        bodyCamResponseDto.setModelo(bodyCam.getModelo());
        bodyCamResponseDto.setNumeroDeSerie(bodyCam.getNumeroDeSerie());
        bodyCamResponseDto.setChip(bodyCam.isChip());
        bodyCamResponseDto.setEstado(bodyCam.getEstado());
        bodyCamResponseDto.setVendedor(bodyCam.getVendedor());
        bodyCamResponseDto.setRevenda(bodyCam.getRevenda());
        bodyCamResponseDto.setSaida(bodyCam.getSaida());
        bodyCamResponseDto.setDevolucao(bodyCam.getDiasAVencer());
        return bodyCamResponseDto;
    }
}
