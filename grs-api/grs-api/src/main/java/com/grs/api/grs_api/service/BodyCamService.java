package com.grs.api.grs_api.service;

import com.grs.api.grs_api.dto.BodyCamPutDto;
import com.grs.api.grs_api.dto.BodyCamRequestDto;
import com.grs.api.grs_api.dto.BodyCamResponseDto;
import com.grs.api.grs_api.exception.*;
import com.grs.api.grs_api.repository.BodyCamRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BodyCamService {

    @Autowired
    private BodyCamRepository repository;

    public BodyCamResponseDto cadastrar(BodyCamRequestDto bodyCamRequestDto) {
        if (repository.existsByNumeroDeSerie(bodyCamRequestDto.getNumeroDeSerie()))
            throw new NumeroDeSerieJaCadastradoException("Número de série '%s' já cadastrado".formatted(bodyCamRequestDto.getNumeroDeSerie()));
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

    public String upload(MultipartFile file) {
        if (file.isEmpty()) throw new EntidadeRequisicaoFalhaException("Arquivo vazio");
        try {
            List<BodyCamRequestDto> bodyCamsList = processarExcel(file);
            return """
                    Arquivo Excel processado com sucesso! 
                    Usuários criados: %d.""".formatted(bodyCamsList.size());
        } catch (Exception e) {
            System.out.println(e);
            throw new EntidadeNaoEncontradaException("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

    private List<BodyCamRequestDto> processarExcel(MultipartFile file) throws IOException {
        List<BodyCamRequestDto> bodyCamRequestDtos = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            boolean primeiraLinha = true;
            List<String> colunas = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (isRowEmpty(row)) continue;

                if (primeiraLinha) {
                    for (Cell cell : row) {
                        colunas.add(cell.getStringCellValue());
                    }
                    primeiraLinha = false;
                    System.out.println(colunas);
                } else {
                    BodyCamRequestDto bodyCamRequestDto = new BodyCamRequestDto();
                    boolean numeroDeSerieVazio = false;

                    for (int colunaAtual = 0; colunaAtual < 7; colunaAtual++) {
                        Cell cell = row.getCell(colunaAtual, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String valorColuna = colunas.get(colunaAtual);
                        if (valorColuna.equals("Número de Série")) {
                            if (cell.getCellType() == CellType.BLANK ||
                                    (cell.getCellType() == CellType.STRING && cell.getStringCellValue().trim().isEmpty())) {
                                numeroDeSerieVazio = true;
                                break;
                            }
                        }

                        switch (valorColuna) {
                            case "Número de Série":
                                if (cell.getCellType() == CellType.STRING)
                                    bodyCamRequestDto.setNumeroDeSerie(cell.getStringCellValue());
                                else if (cell.getCellType() == CellType.NUMERIC)
                                    bodyCamRequestDto.setNumeroDeSerie(String.valueOf(cell.getNumericCellValue()));
                                else {
                                    continue;
                                }
                                break;

                            case "Modelo":
                                bodyCamRequestDto.setModelo(cell.getStringCellValue());
                                break;

                            case "CHIP":
                                if (cell.getCellType() == CellType.BOOLEAN)
                                    bodyCamRequestDto.setChip(cell.getBooleanCellValue());
                                else if (cell.getCellType() == CellType.STRING)
                                    bodyCamRequestDto.setChip(cell.getStringCellValue().equalsIgnoreCase("sim") || cell.getStringCellValue().equalsIgnoreCase("true"));
                                else bodyCamRequestDto.setChip(false);
                                break;

                            case "Estado":
                                if (cell.getCellType() == CellType.STRING)
                                    bodyCamRequestDto.setEstado(cell.getStringCellValue());
                                else if (cell.getCellType() == CellType.NUMERIC)
                                    bodyCamRequestDto.setEstado(String.valueOf(cell.getNumericCellValue()));
                                else bodyCamRequestDto.setEstado("N/A");
                                break;

                            case "Vendedor":
                                bodyCamRequestDto.setVendedor(cell.getStringCellValue());
                                break;
                            case "Revenda":
                                bodyCamRequestDto.setRevenda(cell.getStringCellValue());
                                break;
                            case "Dias a Vencer":
                                if (cell.getCellType() == CellType.NUMERIC)
                                    bodyCamRequestDto.setDiasAVencer((int) cell.getNumericCellValue());
                                else if (cell.getCellType() == CellType.STRING) {
                                    try {
                                        bodyCamRequestDto.setDiasAVencer(Integer.parseInt(cell.getStringCellValue()));
                                    } catch (NumberFormatException e) {
                                        bodyCamRequestDto.setDiasAVencer(0);
                                    }
                                } else bodyCamRequestDto.setDiasAVencer(0);
                                break;
                        }
                    }
                    if (numeroDeSerieVazio) continue;
                        bodyCamRequestDtos.add(bodyCamRequestDto);
                    cadastrar(bodyCamRequestDto);
                }
            }
        }
        return bodyCamRequestDtos;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;

        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
    }
}
