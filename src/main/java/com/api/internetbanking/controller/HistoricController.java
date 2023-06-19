package com.api.internetbanking.controller;

import com.api.internetbanking.models.Historic;
import com.api.internetbanking.service.HistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/historic")
public class HistoricController {

    @Autowired
    private HistoricService historicService;

    @GetMapping
    public ResponseEntity<List<Historic>> getHistoricByDate(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        return ResponseEntity.ok(historicService.findHistoricoByData(data));
    }
}
