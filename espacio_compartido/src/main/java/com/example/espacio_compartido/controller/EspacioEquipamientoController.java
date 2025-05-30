package com.example.espacio_compartido.controller;

import com.example.espacio_compartido.dto.EspacioEquipamientoDTO;
import com.example.espacio_compartido.service.IEspacioEquipamientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/espacio-equipamiento") 
@Validated
public class EspacioEquipamientoController {

    private final IEspacioEquipamientoService espacioEquipamientoService;
    private static final Logger logger = LoggerFactory.getLogger(EspacioEquipamientoController.class);

    @Autowired
    public EspacioEquipamientoController(IEspacioEquipamientoService espacioEquipamientoService) {
        this.espacioEquipamientoService = espacioEquipamientoService;
    }

    @PostMapping
    public ResponseEntity<EspacioEquipamientoDTO> asociarEquipamientoAEspacio(
            @Valid @RequestBody EspacioEquipamientoDTO espacioEquipamientoDTO) {
        logger.info("[ESPACIO-EQUIPAMIENTO] Solicitud para asociar/actualizar equipamiento ID {} a espacio ID {} con cantidad {}.",
                espacioEquipamientoDTO.getIdEquipamiento(), espacioEquipamientoDTO.getIdEspacio(), espacioEquipamientoDTO.getCantidad());
        EspacioEquipamientoDTO result = espacioEquipamientoService.asociarEquipamientoAEspacio(espacioEquipamientoDTO);
        logger.info("[ESPACIO-EQUIPAMIENTO] Asociación creada/actualizada exitosamente.");
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{idEspacio}/{idEquipamiento}")
    public ResponseEntity<EspacioEquipamientoDTO> obtenerEquipamientoDeEspacio(
            @PathVariable Long idEspacio,
            @PathVariable Long idEquipamiento) {
        logger.info("[ESPACIO-EQUIPAMIENTO] Solicitud para obtener asociación de espacio ID {} y equipamiento ID {}.",
                idEspacio, idEquipamiento);
        EspacioEquipamientoDTO association = espacioEquipamientoService.obtenerEquipamientoDeEspacio(idEspacio, idEquipamiento);
        logger.info("[ESPACIO-EQUIPAMIENTO] Asociación encontrada exitosamente.");
        return ResponseEntity.ok(association);
    }

    @DeleteMapping("/{idEspacio}/{idEquipamiento}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<Void> desasociarEquipamientoDeEspacio(
            @PathVariable Long idEspacio,
            @PathVariable Long idEquipamiento) {
        logger.info("[ESPACIO-EQUIPAMIENTO] Solicitud para desasociar equipamiento ID {} de espacio ID {}.",
                idEquipamiento, idEspacio);
        espacioEquipamientoService.desasociarEquipamientoDeEspacio(idEspacio, idEquipamiento);
        logger.info("[ESPACIO-EQUIPAMIENTO] Asociación desasociada exitosamente.");
        return ResponseEntity.noContent().build();
    }

    // El endpoint para LISTAR todo el equipamiento de un espacio iría en EspacioController:
    // @GetMapping("/{idEspacio}/equipamiento") en EspacioController
}