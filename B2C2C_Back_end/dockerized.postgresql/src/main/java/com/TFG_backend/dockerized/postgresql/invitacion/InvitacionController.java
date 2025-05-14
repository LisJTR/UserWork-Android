package com.TFG_backend.dockerized.postgresql.invitacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/invitacion")
public class InvitacionController {

    @Autowired
    private InvitacionRepository invitacionRepository;

    @PostMapping
    public Invitacion crearInvitacion(@RequestBody Invitacion invitacion) {
        System.out.println("🎯 Invitación recibida: " + invitacion);
        invitacion.setFecha(LocalDateTime.now());
        invitacion.setEstado("pendiente");
        return invitacionRepository.save(invitacion);
    }

    @GetMapping
    public List<Invitacion> obtenerTodas() {
        return invitacionRepository.findAll();
    }

    @GetMapping("/empresa/{empresaId}")
    public List<Invitacion> obtenerPorEmpresa(@PathVariable Long empresaId) {
        return invitacionRepository.findByEmpresaId(empresaId);
    }

    @GetMapping("/alumno/{alumnoId}")
    public List<Invitacion> obtenerPorAlumno(@PathVariable Long alumnoId) {
        return invitacionRepository.findByAlumnoId(alumnoId);
    }

    @GetMapping("/oferta/{ofertaId}")
    public List<Invitacion> obtenerPorOferta(@PathVariable Long ofertaId) {
        return invitacionRepository.findByOfertaId(ofertaId);
    }
}