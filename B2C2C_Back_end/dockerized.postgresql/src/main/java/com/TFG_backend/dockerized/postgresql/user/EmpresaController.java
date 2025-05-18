package com.TFG_backend.dockerized.postgresql.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    
    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }

    @GetMapping("/{id}")
    public Empresa getEmpresaById(@PathVariable Long id) {
        return empresaService.obtenerEmpresaPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> createEmpresa(@RequestBody Empresa empresa) {
        if (empresaService.existeEmpresa(empresa.getUsername(), empresa.getCorreoElectronico())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario o correo ya existente");
        }

        Empresa guardada = empresaService.guardarEmpresa(empresa);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/perfil")
    public Empresa updateEmpresa(@RequestBody Empresa empresa) {
        return empresaService.actualizarEmpresa(empresa);
    }

    @GetMapping("/sectores")
    public List<String> getSectoresUnicos() {
        return empresaService.getSectoresUnicos();
    }

    @GetMapping("/perfil")
    public Empresa getPerfilEjemplo() {
        List<Empresa> lista = empresaService.getAllEmpresas();
        return lista.isEmpty() ? null : lista.get(0); // Evita excepción si la lista está vacía
    }
    

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresaConRelaciones(id);
        return ResponseEntity.ok("Empresa y relaciones eliminadas");
    }
    
}