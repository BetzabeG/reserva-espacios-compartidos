package com.example.espacio_compartido.service;

import com.example.espacio_compartido.dto.ReservaDTO;
import com.example.espacio_compartido.dto.HorarioDisponibilidadDTO;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface IReservaService {

    /**
     * Obtiene todas las reservas registradas.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerTodasLasReservas(); 

    /**
     * Obtiene una reserva específica por su ID.
     * @param id ID de la reserva.
     * @return ReservaDTO encontrada.
     * @throws RuntimeException si la reserva no se encuentra.
     */
    ReservaDTO obtenerReservaPorId(Long id); 

    /**
     * Crea una nueva reserva, validando disponibilidad y datos antes de guardarla.
     * asegurar de que pueda recibir varios horarios
     * @param reservaDTO DTO de la reserva a crear.
     * @return ReservaDTO creada.
     * @throws RuntimeException si el espacio no existe o hay solapamiento de horarios.
     */
    ReservaDTO crearReserva(@Valid ReservaDTO reservaDTO); 

    /**
     * Modifica una reserva existente, validando disponibilidad y datos antes de actualizar.
     * @param id ID de la reserva a actualizar.
     * @param reservaDTO DTO con los nuevos datos.
     * @return ReservaDTO actualizada.
     * @throws RuntimeException si la reserva no existe o hay solapamiento de horarios.
     */
    ReservaDTO modificarReserva(Long id, @Valid ReservaDTO reservaDTO); 

    /**
     * Elimina una reserva por su ID.
     * @param id ID de la reserva a eliminar.
     * @throws RuntimeException si la reserva no existe.
     */
    void eliminarReserva(Long id);

    /**
     * Obtiene las reservas de un espacio para una fecha específica.
     * @param espacioId ID del espacio.
     * @param fecha Fecha de la reserva.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorEspacioYFecha(Long espacioId, LocalDate fecha);

    /**
     * Obtiene reservas por reservador, ya sea por ID o por nombre.
     * @param idReservador ID del reservador.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorIdReservador(Long idReservador);

    /**
     * Obtiene reservas por nombre de reservador.
     * @param nombreReservador Nombre del reservador.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorNombreReservador(String nombreReservador);

    /**
     * Obtiene las reservas por facultad.
     * @param facultad Nombre de la facultad.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorFacultad(String facultad);

    /**
     * Obtiene las reservas por carrera.
     * @param carrera Nombre de la carrera.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorCarrera(String carrera);

    /**
     * Obtiene las reservas de un espacio dentro de un rango de fechas.
     * @param espacioId ID del espacio.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de ReservaDTO.
     */
    List<ReservaDTO> obtenerReservasPorEspacioYRangoFechas(Long espacioId, LocalDate fechaInicio, LocalDate fechaFin);



    /**   EL MAS COMPLEJO :(
     * Obtiene los horarios disponibles en un espacio para una fecha específica.
     * Este método permitirá calcular los intervalos libres, evitando conflictos de reserva.
     * @param espacioId ID del espacio.
     * @param fecha Fecha de consulta.
     * @return HorarioDisponibilidadDTO con las horas libres y ocupadas.
     */
    HorarioDisponibilidadDTO obtenerHorariosDisponibles(Long espacioId, LocalDate fecha); 

    /**
     * Filtra reservas aplicando una combinación de filtros opcionales:
     * - facultadId: ID de la facultad
     * - carreraId: ID de la carrera
     * - categoria: categoría del espacio (ej. cancha, laboratorio)
     * - fecha: fecha específica
     * - rango: periodo relativo: 7dias, 1semana, 1mes, año
     *
     * Los filtros pueden usarse de forma combinada.
     *
     * @param facultadId ID de la facultad (opcional)
     * @param carreraId ID de la carrera (opcional)
     * @param categoria Categoría del recurso (opcional)
     * @param fecha Fecha específica (opcional)
     * @param rango Periodo relativo: 7dias, 1semana, 1mes, año (opcional)
     * @return Lista de reservas filtradas.
     */
    List<ReservaDTO> filtrarReservas(Long facultadId,Long carreraId,String categoria,LocalDate fecha,String rango); //SARA

    //metodos futuios  -> 
    // get resevar por facultad 
    // get  reserva por id carrea y idfaculyad
    // get  reserva por id carrer id facultad y id espacio
    // calendario 

}
