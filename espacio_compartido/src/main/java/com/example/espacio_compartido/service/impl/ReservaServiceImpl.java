package com.example.espacio_compartido.service.impl;

import com.example.espacio_compartido.dto.HorarioDisponibilidadDTO;
import com.example.espacio_compartido.dto.ReservaDTO;
import com.example.espacio_compartido.model.Espacio;
import com.example.espacio_compartido.model.Reserva;
import com.example.espacio_compartido.model.Reservador;
import com.example.espacio_compartido.repository.EspacioRepository;
import com.example.espacio_compartido.validation.ReservaValidator;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import com.example.espacio_compartido.repository.ReservaRepository;
import com.example.espacio_compartido.repository.ReservadorRepository;
import com.example.espacio_compartido.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements IReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservadorRepository reservadorRepository;
    private final EspacioRepository espacioRepository;
    private final ReservaValidator reservaValidator;
    //prueba
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

    @Transactional
    @CacheEvict(value = {"reservasPorEstado", "todasLasReservas","reservasPorEspacioYFecha","reservasPorReservador","reservasPorCorreoReservador","reservasFiltradas"}, allEntries = true) // Limpia caché desactualizado
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        reservaDTO.setFechaCreacion(LocalDate.now());
        reservaValidator.validacionCompletaReserva(reservaDTO);

        // Validamos que el reservador exista
        boolean existeReservador = reservadorRepository.existsById(reservaDTO.getIdReservador());
        if (!existeReservador) {
            throw new EntityNotFoundException("El reservador con ID " + reservaDTO.getIdReservador() + " no existe.");
        }

        // Validamos que el espacio exista
        boolean existeEspacio = espacioRepository.existsById(reservaDTO.getIdEspacio());
        if (!existeEspacio) {
            throw new EntityNotFoundException("El espacio con ID " + reservaDTO.getIdEspacio() + " no existe.");
        }
        boolean existe = reservaRepository.existeReserva(reservaDTO.getIdEspacio(), reservaDTO.getFechaReserva(), reservaDTO.getHoraInicio());

        if (existe) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ese espacio ya está ocupado en la fecha y hora seleccionada.");
        }
        Reserva nuevaReserva = convertirAEntidad(reservaDTO);
        nuevaReserva = reservaRepository.save(nuevaReserva);

        return convertirAReservaDTO(nuevaReserva);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"reservasPorEstado", "todasLasReservas","reservasPorEspacioYFecha","reservasPorReservador","reservasPorCorreoReservador","reservasFiltradas"}, allEntries = true)
    public void eliminarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La reserva con ID " + id + " no existe."));

        reservaRepository.delete(reserva);
    }
    @Override
    @Transactional
    @Cacheable(value = "reservasPorEspacioYFecha", key = "#espacioId + '-' + #fechaReserva")
    public List<ReservaDTO> obtenerReservasPorEspacioYFecha(Long espacioId, LocalDate fechaReserva) {
        if (!espacioRepository.existsById(espacioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El espacio con ID " + espacioId + " no existe.");
        }
        reservaValidator.validaFechaReserva(fechaReserva);

        List<Reserva> reservas = reservaRepository.findByEspacioIdAndFechaReserva(espacioId, fechaReserva);
        return reservas.stream()
                    .map(this::convertirAReservaDTO)
                    .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "reservasPorReservador", key = "#idReservador")
    public List<ReservaDTO> obtenerReservasPorIdReservador(Long idReservador) {
        if (idReservador == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de reservador no puede ser nulo.");
        }

        boolean existeReservador = reservadorRepository.existsById(idReservador);
        if (!existeReservador) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El reservador con ID " + idReservador + " no existe.");
        }

        List<Reserva> reservas = reservaRepository.findByIdReservador(idReservador);
        
        return reservas.stream()
                    .map(this::convertirAReservaDTO)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "reservasPorCorreoReservador", key = "#correoReservador")
    public List<ReservaDTO> obtenerReservasPorCorreo(String correoReservador) {

        reservaValidator.validaCorreoReservador(correoReservador);

        List<Reserva> reservas = reservaRepository.findByCorreoReservador(correoReservador);

        return reservas.stream()
                    .map(this::convertirAReservaDTO)
                    .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public ReservaDTO modificarReserva(Long id, ReservaDTO reservaDTO) {
        
        return null;
    }
    //---------------------------------------

    @Override
    public List<ReservaDTO> obtenerReservasPorEspacioYRangoFechas(Long espacioId, LocalDate fechaInicio, LocalDate fechaFin) {
        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con ID: " + espacioId));

        List<Reserva> reservas = reservaRepository.findByEspacioAndEstadoEAndFechaReservaBetween(
            espacio, "CONFIRMADA", fechaInicio, fechaFin
        );

        return reservas.stream()
                .map(this::convertirAReservaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HorarioDisponibilidadDTO obtenerHorariosDisponibles(Long espacioId, LocalDate fecha) {
        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con ID: " + espacioId));

        LocalTime horaApertura = LocalTime.of(8, 0); // 08:00 AM
        LocalTime horaCierre = LocalTime.of(22, 0);  // 10:00 PM

        List<LocalTime> horasDisponibles = new ArrayList<>();
        List<LocalTime> horasOcupadas = new ArrayList<>();

        List<Reserva> reservasDelDia = reservaRepository.findByEspacioAndFechaReservaOrderByHoraInicioAsc(espacio, fecha)
                .stream()
                .filter(r -> r.getEstadoE().equalsIgnoreCase("CONFIRMADA"))
                .collect(Collectors.toList());

        for (LocalTime hora = horaApertura; hora.isBefore(horaCierre); hora = hora.plusHours(1)) {
            LocalTime siguienteHora = hora.plusHours(1);
            boolean ocupada = false;

            for (Reserva reserva : reservasDelDia) {
                if ((hora.isAfter(reserva.getHoraInicio().minusMinutes(1)) && hora.isBefore(reserva.getHoraFin())) ||
                    (siguienteHora.isAfter(reserva.getHoraInicio()) && siguienteHora.isBefore(reserva.getHoraFin().plusMinutes(1))) ||
                    (hora.equals(reserva.getHoraInicio()) && siguienteHora.equals(reserva.getHoraFin()))) {
                    ocupada = true;
                    break;
                }
            }

            if (ocupada) {
                horasOcupadas.add(hora);
            } else {
                horasDisponibles.add(hora);
            }
        }

        return HorarioDisponibilidadDTO.builder()
                .fecha(fecha)
                .espacioId(espacioId)
                .horasDisponibles(horasDisponibles)
                .horasOcupadas(horasOcupadas)
                .build();
    }

    //implemnejszjdg
    @Override
    @Cacheable(value = "reservasFiltradas")
    @Transactional(readOnly = true)
    public List<ReservaDTO> filtrarReservas(String facultad, String carrera, String categoria, LocalDate fecha, String rango) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (rango != null) {
            LocalDate hoy = LocalDate.now();
            switch (rango.toUpperCase()) {
                case "HOY":
                    fechaInicio = hoy;
                    fechaFin = hoy;
                    break;
                case "SEMANA":
                    fechaInicio = hoy;
                    fechaFin = hoy.plusDays(7);
                    break;
                case "MES":
                    fechaInicio = hoy.withDayOfMonth(1);
                    fechaFin = hoy.withDayOfMonth(hoy.lengthOfMonth());
                    break;
                default:
                    throw new IllegalArgumentException("Rango no reconocido: " + rango);
            }
        }

        List<Reserva> reservas = reservaRepository.filtrarReservas(facultad, carrera, categoria, fecha, fechaInicio, fechaFin);

        return reservas.stream()
                .map(this::convertirAReservaDTO)
                .collect(Collectors.toList());
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

