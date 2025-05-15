package com.TFG_backend.dockerized.postgresql.notificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByAlumnoId(Long alumnoId);
    List<Notificacion> findByEmpresaId(Long empresaId);
    List<Notificacion> findByDestinatarioTipoAndAlumnoId(String destinatarioTipo, Long alumnoId);
    List<Notificacion> findByDestinatarioTipoAndEmpresaId(String destinatarioTipo, Long empresaId);
}