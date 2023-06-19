package com.api.internetbanking.repository;

import com.api.internetbanking.models.Historic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoricRepository extends JpaRepository<Historic, Long> {
    List<Historic> findByData(LocalDate data);

}
