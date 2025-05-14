package com.TFG_backend.dockerized.postgresql.invitacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitacionRepository extends JpaRepository<Invitacion, Long> {
    List<Invitacion> findByEmpresaId(Long empresaId);
    List<Invitacion> findByAlumnoId(Long alumnoId);
    List<Invitacion> findByOfertaId(Long ofertaId);
}