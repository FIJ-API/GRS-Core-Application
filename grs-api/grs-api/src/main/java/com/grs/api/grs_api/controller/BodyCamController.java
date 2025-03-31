package com.grs.api.grs_api.controller;

import com.grs.api.grs_api.dto.BodyCamPutDto;
import com.grs.api.grs_api.dto.BodyCamRequestDto;
import com.grs.api.grs_api.dto.BodyCamResponseDto;
import com.grs.api.grs_api.service.BodyCamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("bodycams")
@CrossOrigin("*")
public class BodyCamController {

    @Autowired
    private BodyCamService service;

    @PostMapping
    public ResponseEntity<BodyCamResponseDto> cadastrar(@Valid @RequestBody BodyCamRequestDto bodyCamRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(bodyCamRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<BodyCamResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @DeleteMapping("/{idBodyCam}")
    public ResponseEntity<Void> remover(@NotNull @PathVariable int idBodyCam) {
        service.remover(idBodyCam);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/{idBodyCam}")
    public ResponseEntity<BodyCamResponseDto> atualizar(@NotNull @PathVariable int idBodyCam, @Valid @RequestBody BodyCamPutDto bodyCamPutDto) {
        return ResponseEntity.status(200).body(service.atualizar(idBodyCam, bodyCamPutDto));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestBody MultipartFile file) {
        return ResponseEntity.status(201).body(service.upload(file));
    }

}
