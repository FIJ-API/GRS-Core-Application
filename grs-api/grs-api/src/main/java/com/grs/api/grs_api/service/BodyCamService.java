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

    public String upload(MultipartFile file) {
        if (file.isEmpty()) throw new EntidadeRequisicaoFalhaException("Arquivo vazio");
        try {
            List<BodyCamRequestDto> bodyCamsList = processarExcel(file);
            return "Arquivo Excel processado com sucesso! Usuários criados: " + bodyCamsList.size();
        } catch (Exception e) {
            System.out.println(e);
            throw new EntidadeNaoEncontradaException("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

    private List<BodyCamRequestDto> processarExcel(MultipartFile file) throws IOException {
        List<BodyCamRequestDto> bodyCamRequestDtos = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Obtém a primeira aba
            Iterator<Row> rowIterator = sheet.iterator();
            boolean primeiraLinha = true;
            List<String> colunas = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (primeiraLinha) {
                    for (Cell cell : row) {
                        colunas.add(cell.getStringCellValue());
                    }
                    primeiraLinha = false;
                } else {
                    BodyCamRequestDto bodyCamRequestDto = new BodyCamRequestDto();

                    for (int i = 0; i < colunas.size(); i++) {
                        Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                        switch (colunas.get(i)) {
                            case "Modelo":
                                bodyCamRequestDto.setModelo(cell.getStringCellValue());
                                break;
                            case "NumeroSerie":
                                if (cell.getCellType() == CellType.STRING) {
                                    bodyCamRequestDto.setNumeroDeSerie(cell.getStringCellValue());
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    // Se for numérico, converta para string
                                    bodyCamRequestDto.setNumeroDeSerie(String.valueOf(cell.getNumericCellValue()));
                                } else {
                                    bodyCamRequestDto.setNumeroDeSerie(""); // Valor padrão caso a célula esteja vazia ou com formato inesperado
                                }
                                break;
                            case "CHIP":
                                if (cell.getCellType() == CellType.BOOLEAN) {
                                    bodyCamRequestDto.setChip(cell.getBooleanCellValue());
                                } else if (cell.getCellType() == CellType.STRING) {
                                    bodyCamRequestDto.setChip(cell.getStringCellValue().equalsIgnoreCase("sim") || cell.getStringCellValue().equalsIgnoreCase("true"));
                                } else {
                                    bodyCamRequestDto.setChip(false);
                                }
                                break;
                            case "Estado":
                                if (cell.getCellType() == CellType.STRING) {
                                    bodyCamRequestDto.setEstado(cell.getStringCellValue());
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    // Se for numérico, converta para string
                                    bodyCamRequestDto.setEstado(String.valueOf(cell.getNumericCellValue()));
                                } else {
                                    bodyCamRequestDto.setEstado(""); // Valor padrão caso a célula esteja vazia ou com formato inesperado
                                }
                                break;
                            case "Vendedor":
                                bodyCamRequestDto.setVendedor(cell.getStringCellValue());
                                break;
                            case "Revenda":
                                bodyCamRequestDto.setRevenda(cell.getStringCellValue());
                                break;
                            case "Dias a Vencer":
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    bodyCamRequestDto.setDiasAVencer((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == CellType.STRING) {
                                    try {
                                        bodyCamRequestDto.setDiasAVencer(Integer.parseInt(cell.getStringCellValue()));
                                    } catch (NumberFormatException e) {
                                        bodyCamRequestDto.setDiasAVencer(0); // Exemplo de valor padrão
                                    }
                                } else {
                                    bodyCamRequestDto.setDiasAVencer(0); // Exemplo de valor padrão
                                }
                                break;
                        }

                    }

                    bodyCamRequestDtos.add(bodyCamRequestDto);
                    cadastrar(bodyCamRequestDto);
                }
            }
        }
        return bodyCamRequestDtos;
    }
}
