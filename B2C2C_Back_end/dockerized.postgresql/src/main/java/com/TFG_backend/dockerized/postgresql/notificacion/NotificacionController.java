package com.TFG_backend.dockerized.postgresql.notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @PostMapping
    public Notificacion crearNotificacion(@RequestBody Notificacion notificacion) {
    	   System.out.println("📩 Notificación recibida en el backend: " + notificacion);
        return notificacionRepository.save(notificacion);
    }

    @GetMapping("/alumno/{alumnoId}")
    public List<Notificacion> obtenerNotificacionesAlumno(@PathVariable Long alumnoId) {
        return notificacionRepository.findByDestinatarioTipoAndAlumnoId("alumno", alumnoId);
    }

    @GetMapping("/empresa/{empresaId}")
    public List<Notificacion> obtenerNotificacionesEmpresa(@PathVariable Long empresaId) {
        return notificacionRepository.findByDestinatarioTipoAndEmpresaId("empresa", empresaId);
    }
    
    @PutMapping
    public Notificacion actualizarNotificacion(@RequestBody Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

}
