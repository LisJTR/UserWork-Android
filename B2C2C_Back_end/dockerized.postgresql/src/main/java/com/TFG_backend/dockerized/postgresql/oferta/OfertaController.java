package com.TFG_backend.dockerized.postgresql.oferta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/oferta")
public class OfertaController {

    @Autowired
    private OfertaRepository ofertaRepository;

    @GetMapping
    public List<Oferta> getAllOferta() {
        return ofertaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Oferta getOfertaById(@PathVariable Long id) {
        return ofertaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Oferta createOferta(@RequestBody Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    @PutMapping("/perfil") // <- Aquí el path ya incluye "/api/alumno"
    public Oferta updateOferta(@RequestBody Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    @GetMapping("/perfil")
    public Oferta getPerfilEjemplo() {
        return ofertaRepository.findAll().get(0); //  esto es solo de prueba
    }
}