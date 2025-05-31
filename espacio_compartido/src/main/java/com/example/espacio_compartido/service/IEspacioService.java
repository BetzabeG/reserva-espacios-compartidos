package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.EspacioDTO;
import com.example.espacio_compartido.model.Espacio;
import java.util.List;

public interface IEspacioService {
    List<EspacioDTO> obtenerTodosLosEspacios();
    EspacioDTO obtenerEspacioPorId(Long id);
    EspacioDTO crearEspacio(EspacioDTO espacioDTO);
    EspacioDTO actualizarEspacio(Long id, EspacioDTO espacioDTO);
    EspacioDTO eliminarEspacio(Long id);
    void eliminarEspacioFisicamente(Long id);
    Espacio obtenerEntidadEspacioPorId(Long id);
    List<EspacioDTO> obtenerEspaciosPorEstado(Boolean estado);
    
}