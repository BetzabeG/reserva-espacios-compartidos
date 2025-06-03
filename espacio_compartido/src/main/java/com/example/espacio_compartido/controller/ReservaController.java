package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.ReservaDTO;
import com.example.espacio_compartido.service.IReservaService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
public class ReservaController {

    private final IReservaService reservaService;
    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    public ReservaController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> obtenerTodasLasReservas() {
        long inicio = System.currentTimeMillis();
        logger.info("[RESERVA] Inicio obtenerTodasLasReservas: {}", inicio);

        List<ReservaDTO> reservas = reservaService.obtenerTodasLasReservas();

        long fin = System.currentTimeMillis();
        logger.info("[RESERVA] Fin obtenerTodasLasReservas: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(reservas);
    }

}
