package com.example.espacio_compartido.repository;

import com.example.espacio_compartido.model.Espacio;
import com.example.espacio_compartido.model.Reserva;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {


    /**
     * Bloqueo pesimista
     * @param id
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Reserva r WHERE r.idReserva = :id")
    Optional<Reserva> findByIdWithLock(@Param("id") Long id);
    
    
    /**
     * Obtiene reservas filtradas por estado
     * @param estado Estado de la reserva (ej. "CONFIRMADA", "CANCELADA").
     * @return Lista de reservas con el estado especificado.
     */
    List<Reserva> findByEstadoE(String estado); 

    /**
     * metodoo extra pare crear reserva
     */
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.espacio.idEspacio = :idEspacio AND r.fechaReserva = :fechaReserva AND r.horaInicio = :horaInicio")
    boolean existeReserva(@Param("idEspacio") Long idEspacio, @Param("fechaReserva") LocalDate fechaReserva, @Param("horaInicio") LocalTime horaInicio);

    @Modifying
    @Query("DELETE FROM Reserva r WHERE r.espacio.idEspacio = :idEspacio")
    void eliminarReservasPorEspacio(@Param("idEspacio") Long idEspacio);

    @Query("SELECT r FROM Reserva r WHERE r.espacio.idEspacio = :espacioId AND r.fechaReserva = :fechaReserva")
    List<Reserva> findByEspacioIdAndFechaReserva(@Param("espacioId") Long espacioId, @Param("fechaReserva") LocalDate fechaReserva);

    //metodo para encontrar las reserva de un idreservador
    @Query("SELECT r FROM Reserva r WHERE r.reservador.id = :idReservador")
    List<Reserva> findByIdReservador(@Param("idReservador") Long idReservador);

    //metodo para obetner reservar en base a un correo
    @Query("SELECT r FROM Reserva r JOIN r.reservador res ON r.reservador.id = res.id WHERE res.correoInstitucional = :correoReservador")
    List<Reserva> findByCorreoReservador(@Param("correoReservador") String correoReservador);


    //---------------------
    @Query("SELECT r FROM Reserva r " +
        "JOIN r.espacio e " +
        "JOIN e.categoria c " +
        "WHERE (:facultad IS NULL OR e.facultad = :facultad) " +
        "AND (:carrera IS NULL OR e.carrera = :carrera) " +
        "AND (:categoria IS NULL OR c.nombre = :categoria) " +
        "AND (:fecha IS NULL OR r.fechaReserva = :fecha) " +
        "AND (:fechaInicio IS NULL OR :fechaFin IS NULL OR r.fechaReserva BETWEEN :fechaInicio AND :fechaFin)")
    List<Reserva> filtrarReservas(
            @Param("facultad") String facultad,
            @Param("carrera") String carrera,
            @Param("categoria") String categoria,
            @Param("fecha") LocalDate fecha,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    List<Reserva> findByEspacioAndEstadoEAndFechaReservaBetween(
        Espacio espacio, String estadoE, LocalDate fechaInicio, LocalDate fechaFin
    );

    List<Reserva> findByEspacioAndFechaReservaOrderByHoraInicioAsc(Espacio espacio, LocalDate fechaReserva);



//prueba


}

