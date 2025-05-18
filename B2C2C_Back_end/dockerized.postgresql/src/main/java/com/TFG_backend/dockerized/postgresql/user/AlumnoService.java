package com.TFG_backend.dockerized.postgresql.user;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TFG_backend.dockerized.postgresql.aplicacion.AplicacionOfertaRepository;
import com.TFG_backend.dockerized.postgresql.config.PasswordEncoderUtil;
import com.TFG_backend.dockerized.postgresql.invitacion.InvitacionRepository;
import com.TFG_backend.dockerized.postgresql.notificacion.NotificacionRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AplicacionOfertaRepository aplicacionRepo;

    @Autowired
    private NotificacionRepository notificacionRepo;

    @Autowired
    private InvitacionRepository invitacionRepo;

    @Autowired
    private AlumnoRepository alumnoRepo;

    
    public boolean existeAlumno(String username, String correo) {
        return alumnoRepository.findByUsernameOrCorreoElectronico(username, correo) != null;
    }

    public Alumno guardarAlumno(Alumno alumno) {
        alumno.setPassword(PasswordEncoderUtil.encode(alumno.getPassword()));
        return alumnoRepository.save(alumno);
    }

    public Alumno actualizarAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public Alumno obtenerAlumnoPorId(Long id) {
        return alumnoRepository.findById(id).orElse(null);
    }
    
    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    public List<String> getTitulacionesUnicas() {
        return alumnoRepository.findAll()
            .stream()
            .map(Alumno::getTitulacion)
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .distinct()
            .collect(Collectors.toList());
    }
    
    @Transactional
    public void eliminarAlumnoConRelaciones(Long alumnoId) {
        aplicacionRepo.deleteByAlumnoId(alumnoId);
        notificacionRepo.deleteByAlumnoId(alumnoId);
        invitacionRepo.deleteByAlumnoId(alumnoId);
        alumnoRepo.deleteById(alumnoId);
    }
}