package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.EspacioEquipamientoDTO;
import java.util.List;

public interface IEspacioEquipamientoService {

    EspacioEquipamientoDTO asociarEquipamientoAEspacio(EspacioEquipamientoDTO espacioEquipamientoDTO);

    EspacioEquipamientoDTO obtenerEquipamientoDeEspacio(Long idEspacio, Long idEquipamiento);

    List<EspacioEquipamientoDTO> obtenerEquipamientosPorIdEspacio(Long idEspacio); 

    void desasociarEquipamientoDeEspacio(Long idEspacio, Long idEquipamiento);
<<<<<<< Updated upstream
}
=======

    EspacioEquipamientoDTO actualizarCantidadEquipamientoEnEspacio(Long idEspacio, Long idEquipamiento, Integer cantidad);


    /*DEATELLE */
List<EspacioEquipamiento> listarPorIdEspacio(Long idEspacio);}
>>>>>>> Stashed changes
