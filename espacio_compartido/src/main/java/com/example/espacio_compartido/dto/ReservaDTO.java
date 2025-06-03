package com.example.espacio_compartido.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {

    @NotNull(message = "El identificador del reservador es obligatorio")
    @Positive(message = "El ID del reservador debe ser un número positivo")
    private Long idReservador;

    @NotNull(message = "El identificador del espacio es obligatorio")
    @Positive(message = "El ID del espacio debe ser un número positivo")
    private Long idEspacio;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación no puede ser en el futuro")
    private LocalDate fechaCreacion;

    @NotNull(message = "La fecha de reserva es obligatoria")
    @FutureOrPresent(message = "La fecha de reserva debe ser hoy o en el futuro")
    private LocalDate fechaReserva;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Future(message = "La hora de fin debe ser después de la hora actual")
    private LocalTime horaFin;

    @Size(max = 255, message = "El motivo no puede tener más de 255 caracteres")
    private String motivo;

    @NotBlank(message = "El estado de la reserva es obligatorio")
    @Pattern(regexp = "PENDIENTE|CONFIRMADA|CANCELADA|FINALIZADA", message = "El estado debe ser PENDIENTE, CONFIRMADA, CANCELADA o FINALIZADA")
    private String estadoE;
}
