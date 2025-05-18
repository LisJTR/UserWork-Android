package com.TFG_backend.dockerized.postgresql.user;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.TFG_backend.dockerized.postgresql.config.PasswordEncoderUtil;

@RestController
@RequestMapping("/api/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;
    
 
    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnoService.getAllAlumnos(); // puedes mover esto también al service si quieres
    }

    @GetMapping("/{id}")
    public Alumno getAlumnoById(@PathVariable Long id) {
        return alumnoService.obtenerAlumnoPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> createAlumno(@RequestBody Alumno alumno) {
        if (alumnoService.existeAlumno(alumno.getUsername(), alumno.getCorreoElectronico())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario o correo ya existente");
        }

        Alumno guardado = alumnoService.guardarAlumno(alumno);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/perfil")
    public Alumno updateAlumno(@RequestBody Alumno alumno) {
        return alumnoService.actualizarAlumno(alumno);
    }

    @GetMapping("/titulaciones")
    public List<String> getTitulacionesUnicas() {
        return alumnoService.getTitulacionesUnicas(); // este método puedes moverlo si quieres también
    }
    

    
}