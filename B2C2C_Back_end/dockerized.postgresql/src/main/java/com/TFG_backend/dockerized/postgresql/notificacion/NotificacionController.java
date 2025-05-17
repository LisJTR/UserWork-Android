package com.TFG_backend.dockerized.postgresql.notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    	  System.out.println("📩 Buscando notificaciones para empresa ID: " + empresaId);
        return notificacionRepository.findByDestinatarioTipoAndEmpresaId("empresa", empresaId);
    }
    
    @PutMapping("/{id}/{estado}")
    public Notificacion actualizarEstadoRespuesta(
            @PathVariable Long id,
            @PathVariable String estado) {

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));

        notificacion.setEstadoRespuesta(estado);
        return notificacionRepository.save(notificacion);
    }
    
    @PutMapping("/{id}/marcar-leida")
    public Notificacion marcarComoLeida(@PathVariable Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));

        notificacion.setLeido(true);
        return notificacionRepository.save(notificacion);
    }
    
 



}
