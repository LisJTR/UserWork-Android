package com.TFG_backend.dockerized.postgresql.aplicacion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aplicacion")
public class AplicacionOfertaController {

    @Autowired
    private AplicacionOfertaRepository aplicacionRepository;

    @PostMapping
    public AplicacionOferta crearAplicacion(@RequestBody AplicacionOferta aplicacion) {
        System.out.println("🛠️ Recibida aplicación con ofertaId = " + aplicacion.getOfertaId());
        return aplicacionRepository.save(aplicacion);
    }

    @GetMapping("/alumno/{id}")
    public List<AplicacionOferta> getAplicacionesPorAlumno(@PathVariable Long id) {
        return aplicacionRepository.findByAlumnoId(id);
    }

    @GetMapping("/oferta/{id}")
    public List<AplicacionOferta> getAplicacionesPorOferta(@PathVariable Long id) {
        return aplicacionRepository.findByOfertaId(id);
    }

    @GetMapping
    public List<AplicacionOferta> getTodas() {
        return aplicacionRepository.findAll();
    }
}