package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.HorarioDisponibilidadDTO;
import com.example.espacio_compartido.dto.ReservaDTO;
import com.example.espacio_compartido.model.Espacio;
import com.example.espacio_compartido.model.Reserva;
import com.example.espacio_compartido.model.Reservador;
import com.example.espacio_compartido.repository.EspacioRepository;
import com.example.espacio_compartido.validation.ReservaValidator;

import jakarta.persistence.EntityNotFoundException;

import com.example.espacio_compartido.repository.ReservaRepository;
import com.example.espacio_compartido.repository.ReservadorRepository;
import com.example.espacio_compartido.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements IReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservadorRepository reservadorRepository;
    private final EspacioRepository espacioRepository;
    private final ReservaValidator reservaValidator;

    @Autowired
    public ReservaServiceImpl(ReservaRepository reservaRepository, ReservadorRepository reservadorRepository, EspacioRepository espacioRepository, ReservaValidator reservaValidator) {
        this.reservaRepository = reservaRepository;
        this.reservadorRepository = reservadorRepository;
        this.espacioRepository = espacioRepository;
        this.reservaValidator = reservaValidator;
    }

    @Override
    @Cacheable(value = "todasLasReservas") 
    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerTodasLasReservas() {
        List<Reserva> reservas = reservaRepository.findAll(); 

        return reservas.stream()
                .map(this::convertirAReservaDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Cacheable(value = "reservaPorId", key = "#id")
    @Transactional(readOnly = true)
    public ReservaDTO obtenerReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));

        return convertirAReservaDTO(reserva);
    }
    @Override
    @Cacheable(value = "reservasPorEstado", key = "#estado") // Almacena en caché cada búsqueda por estado
    @Transactional(readOnly = true) // Solo lectura, evita bloqueos innecesarios
    public List<ReservaDTO> obtenerReservasPorEstado(String estado) {
        List<Reserva> reservas = reservaRepository.findByEstadoE(estado);

        if (reservas.isEmpty()) {
            throw new EntityNotFoundException("No hay reservas con estado: " + estado);
        }

        return reservas.stream()
                .map(this::convertirAReservaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        // Implementación futura
        return null;
    }

    @Override
    @Transactional
    public ReservaDTO modificarReserva(Long id, ReservaDTO reservaDTO) {
        // Implementación futura
        return null;
    }

    @Override
    @Transactional
    public void eliminarReserva(Long id) {
        // Implementación futura
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorEspacioYFecha(Long espacioId, LocalDate fecha) {
        // Implementación futura
        return null;
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorIdReservador(Long idReservador) {
        // Implementación futura
        return null;
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorNombreReservador(String nombreReservador) {
        // Implementación futura
        return null;
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorFacultad(String facultad) {
        // Implementación futura
        return null;
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorCarrera(String carrera) {
        // Implementación futura
        return null;
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorEspacioYRangoFechas(Long espacioId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Implementación futura
        return null;
    }

    @Override
    public HorarioDisponibilidadDTO obtenerHorariosDisponibles(Long espacioId, LocalDate fecha) {
        // Implementación futura
        return null;
    }
    @Override
    public List<ReservaDTO> filtrarReservas(Long facultadId, Long carreraId, String categoria, LocalDate fecha, String rango) {
        // Implementación futura
        return null;
    }


    private ReservaDTO convertirAReservaDTO(Reserva reserva) {
        return ReservaDTO.builder()
                .idReserva(reserva.getIdReserva()) // ← Se agrega la ID de reserva
                .idReservador(reserva.getReservador().getIdReservador())
                .idEspacio(reserva.getEspacio().getIdEspacio())
                .fechaCreacion(reserva.getFechaCreacion())
                .fechaReserva(reserva.getFechaReserva())
                .horaInicio(reserva.getHoraInicio())
                .horaFin(reserva.getHoraFin())
                .motivo(reserva.getMotivo())
                .estadoE(reserva.getEstadoE())
                .build();
    }


    private Reserva convertirAEntidad(ReservaDTO reservaDTO) {
        return Reserva.builder()
                .idReserva(reservaDTO.getIdReserva()) // ← Se agrega la ID de reserva
                .reservador(Reservador.builder().idReservador(reservaDTO.getIdReservador()).build())
                .espacio(Espacio.builder().idEspacio(reservaDTO.getIdEspacio()).build())
                .fechaCreacion(reservaDTO.getFechaCreacion())
                .fechaReserva(reservaDTO.getFechaReserva())
                .horaInicio(reservaDTO.getHoraInicio())
                .horaFin(reservaDTO.getHoraFin())
                .motivo(reservaDTO.getMotivo())
                .estadoE(reservaDTO.getEstadoE())
                .build();
    }

}

