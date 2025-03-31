package com.grs.api.grs_api.service;

import com.grs.api.grs_api.dto.BodyCamPutDto;
import com.grs.api.grs_api.dto.BodyCamRequestDto;
import com.grs.api.grs_api.dto.BodyCamResponseDto;
import com.grs.api.grs_api.exception.EntidadeSemRetornoException;
import com.grs.api.grs_api.exception.NumeroDeSerieJaCadastradoException;
import com.grs.api.grs_api.repository.BodyCamRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyCamService {

    @Autowired
    private BodyCamRepository repository;

    public BodyCamResponseDto cadastrar(BodyCamRequestDto bodyCamRequestDto) {
        if (repository.existsByNumeroDeSerie(bodyCamRequestDto.getNumeroDeSerie())) throw new NumeroDeSerieJaCadastradoException("Número de série '%s' já cadastrado".formatted(bodyCamRequestDto.getNumeroDeSerie()));
        return repository.insertInto(bodyCamRequestDto);
    }

    public List<BodyCamResponseDto> listar() {
        List<BodyCamResponseDto> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma BodyCam encontrada");
        return all;
    }

    public void remover(int id) {
        repository.deleteWhere(id);
    }


    public BodyCamResponseDto atualizar(@NotNull int idBodyCam, @Valid BodyCamPutDto bodyCamPutDto) {
        return repository.update(idBodyCam, bodyCamPutDto);
    }
}
