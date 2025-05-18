package com.TFG_backend.dockerized.postgresql.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TFG_backend.dockerized.postgresql.config.PasswordEncoderUtil;

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

       // if (alumno != null && alumno.getPassword().equals(request.getPassword())) {
           // return ResponseEntity.ok(new LoginResponse("Login exitoso", alumno.getId(), "alumno"));
        //}
        
        if (alumno != null && PasswordEncoderUtil.matches(request.getPassword(), alumno.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Login exitoso", alumno.getId(), "alumno"));
        }

        var empresa = empresaRepository.findByUsernameOrCorreoElectronico(
            request.getUsername(), request.getCorreo_electronico());

        //if (empresa != null && empresa.getPassword().equals(request.getPassword())) {
          //  return ResponseEntity.ok(new LoginResponse("Login exitoso", empresa.getId(), "empresa"));
       // }
        
        if (empresa != null && PasswordEncoderUtil.matches(request.getPassword(), empresa.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Login exitoso", empresa.getId(), "empresa"));
        }

        return ResponseEntity.status(401).body(new LoginResponse("Credenciales incorrectas", null, null));
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody CambiarPasswordRequest request) {
        if (request.getTipo().equals("alumno")) {
            Alumno alumno = alumnoRepository.findById(request.getId()).orElse(null);
            if (alumno != null && PasswordEncoderUtil.matches(request.getPasswordActual(), alumno.getPassword())) {
                alumno.setPassword(PasswordEncoderUtil.encode(request.getPasswordNueva()));
                alumnoRepository.save(alumno);
                return ResponseEntity.ok("Contraseña cambiada con éxito");
            }
        } else if (request.getTipo().equals("empresa")) {
            Empresa empresa = empresaRepository.findById(request.getId()).orElse(null);
            if (empresa != null && PasswordEncoderUtil.matches(request.getPasswordActual(), empresa.getPassword())) {
                empresa.setPassword(PasswordEncoderUtil.encode(request.getPasswordNueva()));
                empresaRepository.save(empresa);
                return ResponseEntity.ok("Contraseña cambiada con éxito");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña actual incorrecta");
    }

    
}