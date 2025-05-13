package com.TFG_backend.dockerized.postgresql.aplicacion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AplicacionOfertaRepository extends JpaRepository<AplicacionOferta, Long> {
    List<AplicacionOferta> findByAlumnoId(Long alumnoId);
    List<AplicacionOferta> findByOfertaId(Long ofertaId);
}