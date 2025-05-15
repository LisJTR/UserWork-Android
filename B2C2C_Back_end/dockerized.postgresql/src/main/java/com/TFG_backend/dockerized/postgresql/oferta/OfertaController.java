package com.TFG_backend.dockerized.postgresql.oferta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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
    public ResponseEntity<?> createOferta(@RequestBody Oferta oferta) {
        try {
            System.out.println("🟢 Recibida oferta: " + oferta);
            Oferta saved = ofertaRepository.save(oferta);
            System.out.println("✅ Oferta guardada con ID: " + saved.getId());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            System.out.println("❌ Error al guardar la oferta:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la oferta");
        }
    }

    @PutMapping("/perfil") // <- Aquí el path ya incluye "/api/alumno"
    public Oferta updateOferta(@RequestBody Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    @GetMapping("/perfil")
    public Oferta getPerfilEjemplo() {
        return ofertaRepository.findAll().get(0); //  esto es solo de prueba
    }
    
    @GetMapping("/empresa/{id}")
    public List<Oferta> getOfertasByEmpresaId(@PathVariable Long id) {
        return ofertaRepository.findByEmpresaId(id);
    }
    
    @DeleteMapping("/{id}")
    public void deleteOferta(@PathVariable Long id) {
        ofertaRepository.deleteById(id);
    }
    
    @GetMapping("/todas")
    public List<Oferta> getTodasLasOfertas() {
        return ofertaRepository.findAll();
    }

}