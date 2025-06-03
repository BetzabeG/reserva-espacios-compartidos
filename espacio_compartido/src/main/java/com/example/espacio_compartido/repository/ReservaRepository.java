package com.example.espacio_compartido.repository;

import com.example.espacio_compartido.model.Reserva;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



import java.util.Optional;
import java.time.LocalDate;
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
    Optional<Reserva> findByIdWithLock(@Param("idReserva") Long id);
        /**
     * Obtiene reservas filtradas por estado
     * @param estado Estado de la reserva (ej. "CONFIRMADA", "CANCELADA").
     * @return Lista de reservas con el estado especificado.
     */
    List<Reserva> findByEstadoE(String estado); 


}

