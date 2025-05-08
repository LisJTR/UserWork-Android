package com.TFG_backend.dockerized.postgresql.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var alumno = alumnoRepository.findByUsernameOrCorreoElectronico(
            request.getUsername(), request.getCorreo_electronico());

        if (alumno != null && alumno.getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Login exitoso", alumno.getId(), "alumno"));
        }

        var empresa = empresaRepository.findByUsernameOrCorreoElectronico(
            request.getUsername(), request.getCorreo_electronico());

        if (empresa != null && empresa.getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Login exitoso", empresa.getId(), "empresa"));
        }

        return ResponseEntity.status(401).body(new LoginResponse("Credenciales incorrectas", null, null));
    }
    
}