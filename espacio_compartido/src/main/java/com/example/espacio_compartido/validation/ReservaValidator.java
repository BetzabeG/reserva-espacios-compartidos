package com.example.espacio_compartido.validation;

import org.springframework.stereotype.Component;
import com.example.espacio_compartido.dto.ReservaDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

@Component
public class ReservaValidator {

    private static final Pattern ESTADO_PATTERN = Pattern.compile("^(PENDIENTE|CONFIRMADA|CANCELADA|FINALIZADA)$");
    private static final int MOTIVO_MAX_LENGTH = 255;

    public void validaFechaCreacion(LocalDate fechaCreacion) {
        if (fechaCreacion == null || fechaCreacion.isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de creación no puede ser en el futuro.");
        }
    }

    public void validaFechaReserva(LocalDate fechaReserva) {
        if (fechaReserva == null || fechaReserva.isBefore(LocalDate.now())) {
            throw new BusinessException("La fecha de reserva debe ser hoy o en el futuro.");
        }
    }

    public void validaHoras(LocalTime horaInicio, LocalTime horaFin) {
        if (horaInicio == null || horaFin == null) {
            throw new BusinessException("Las horas de inicio y fin son obligatorias.");
        }
        if (!horaInicio.isBefore(horaFin)) {
            throw new BusinessException("La hora de inicio debe ser anterior a la hora de fin.");
        }
        if (horaInicio.isBefore(LocalTime.of(7, 0)) || horaFin.isAfter(LocalTime.of(22, 0))) {
            throw new BusinessException("El horario de reserva debe estar entre 07:00 y 22:00.");
        }
    }

    public void validaEstado(String estadoE) {
        if (estadoE == null || !ESTADO_PATTERN.matcher(estadoE).matches()) {
            throw new BusinessException("Estado no válido. Debe ser uno de: PENDIENTE, CONFIRMADA, CANCELADA, FINALIZADA.");
        }
    }
     public void validaMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new BusinessException("El motivo de la reserva es obligatorio.");
        }
        if (motivo.length() > MOTIVO_MAX_LENGTH) {
            throw new BusinessException("El motivo no puede superar los 255 caracteres.");
        }
    }

        public void validacionCompletaReserva(ReservaDTO reservaDTO) {
        validaFechaCreacion(reservaDTO.getFechaCreacion());
        validaFechaReserva(reservaDTO.getFechaReserva());
        validaHoras(reservaDTO.getHoraInicio(), reservaDTO.getHoraFin());
        validaMotivo(reservaDTO.getMotivo());
        validaEstado(reservaDTO.getEstadoE());
    }

    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}

