package com.grs.api.grs_api.repository;

import com.grs.api.grs_api.dto.BodyCamRequestDto;
import com.grs.api.grs_api.dto.BodyCamResponseDto;
import com.grs.api.grs_api.entity.BodyCam;
import com.grs.api.grs_api.exception.EntidadeNaoEncontradaException;
import com.grs.api.grs_api.exception.EntidadeRequisicaoFalhaException;
import com.grs.api.grs_api.mapper.BodyCamMapper;
import com.grs.api.grs_api.service.BodyCamService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BodyCamRepository {

    private final JdbcClient jdbcClient;

    public BodyCamRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public boolean existsByNumeroDeSerie(@NotBlank String numeroDeSerie) {
        if (this.jdbcClient
                .sql("SELECT 1 FROM bodycam WHERE numeroDeSerie = ?")
                .param(numeroDeSerie)
                .query(Integer.class)
                .optional()
                .isPresent()) return true;
        return false;
    }

    public BodyCamResponseDto insertInto(BodyCamRequestDto bodyCamRequestDto) {
        BodyCam bodyCam = BodyCamMapper.toEntity(bodyCamRequestDto);
        int result = jdbcClient.sql("INSERT INTO bodycam (modelo, numeroDeSerie, chip, estado, vendedor, revenda, saida, diasAVencer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                .param(bodyCam.getModelo())
                .param(bodyCam.getNumeroDeSerie())
                .param(bodyCam.isChip())
                .param(bodyCam.getEstado())
                .param(bodyCam.getVendedor())
                .param(bodyCam.getRevenda())
                .param(bodyCam.getSaida())
                .param(bodyCam.getDiasAVencer())
                .update();

        if (result > 0) {;
            bodyCam.setIdBodyCam(jdbcClient.sql("SELECT LAST_INSERT_ID()")
                    .query(Integer.class).single());

            return BodyCamMapper.toDto(bodyCam);
        } throw new EntidadeRequisicaoFalhaException("Falha na inserção de empresa");
    }

    public List<BodyCamResponseDto> selectAll() {
        List<BodyCam> bodyCams =  this.jdbcClient
                .sql("SELECT * FROM bodycam")
                .query(BodyCam.class)
                .list();
        List<BodyCamResponseDto> bodyCamResponseDtos = new ArrayList<>();
        for (BodyCam bodyCam : bodyCams) {
            bodyCamResponseDtos.add(BodyCamMapper.toDto(bodyCam));
        }
        return bodyCamResponseDtos;
    }

    public boolean deleteWhere(int id) {
        existsById(id);

        return this.jdbcClient
                .sql("DELETE FROM bodycam WHERE idBodyCam = ?")
                .param(id)
                .update() > 0;
    }

    public void existsById(int id) {
        if (!this.jdbcClient
                .sql("SELECT 1 FROM bodycam WHERE idBodyCam = ?")
                .param(id)
                .query(Integer.class)
                .optional()
                .isPresent()) throw new EntidadeNaoEncontradaException("BodyCam com o ID " + id + " não encontrado.");
    }
}
