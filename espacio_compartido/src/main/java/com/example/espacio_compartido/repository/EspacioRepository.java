package com.example.espacio_compartido.repository;

import com.example.espacio_compartido.model.Espacio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Espacio e WHERE e.idEspacio = :id")
    Optional<Espacio> findByIdWithLock(Long id);

    List<Espacio> findByEstado(Boolean estado);
}