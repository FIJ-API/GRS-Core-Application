package com.grs.api.grs_api.controller;

import com.grs.api.grs_api.dto.BodyCamRequestDto;
import com.grs.api.grs_api.dto.BodyCamResponseDto;
import com.grs.api.grs_api.service.BodyCamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bodycams")
@CrossOrigin("*")
public class BodyCamController {

    @Autowired
    private BodyCamService service;

    @PostMapping
    public ResponseEntity<BodyCamResponseDto> cadastrar(@RequestBody BodyCamRequestDto bodyCamRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(bodyCamRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<BodyCamResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }
}
