package com.TFG_backend.dockerized.postgresql.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Empresa getEmpresaById(@PathVariable Long id) {
        return empresaRepository.findById(id).orElse(null);
    }


	@PostMapping
	public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
	    Empresa empresaGuardada = empresaRepository.save(empresa);
	    return ResponseEntity.ok(empresaGuardada); // ✅ El ID generado ya estará presente aquí
	}

    @PutMapping("/perfil")
    public Empresa updateEmpresa(@RequestBody Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @GetMapping("/perfil")
    public Empresa getPerfilEjemplo() {
        return empresaRepository.findAll().get(0); // solo para pruebas
    }
}