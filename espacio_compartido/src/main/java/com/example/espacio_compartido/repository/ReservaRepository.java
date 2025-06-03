package com.example.espacio_compartido.repository;

import com.example.espacio_compartido.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Obtiene todas las reservas por estados específicos.
     * @param estados Lista de estados permitidos (ej. "CONFIRMADA", "PENDIENTE").
     * @return Lista de reservas activas.
     */
    List<Reserva> findByEstadoEIn(List<String> estados);

}

