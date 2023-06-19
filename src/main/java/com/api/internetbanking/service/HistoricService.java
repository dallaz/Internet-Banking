package com.api.internetbanking.service;

import com.api.internetbanking.models.Historic;
import com.api.internetbanking.repository.HistoricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoricService {

    @Autowired
    HistoricRepository historicRepository;

    public List<Historic> findHistoricoByData(LocalDate data) {
        return historicRepository.findByData(data);
    }

    public void saveHistoric(Historic historic) {
        historicRepository.save(historic);
    }
}
