package com.TFG_backend.dockerized.postgresql.user;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TFG_backend.dockerized.postgresql.config.PasswordEncoderUtil;
import com.TFG_backend.dockerized.postgresql.invitacion.InvitacionRepository;
import com.TFG_backend.dockerized.postgresql.notificacion.NotificacionRepository;
import com.TFG_backend.dockerized.postgresql.oferta.OfertaRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private OfertaRepository ofertaRepo;

    @Autowired
    private NotificacionRepository notificacionRepo;

    @Autowired
    private InvitacionRepository invitacionRepo;

    @Autowired
    private EmpresaRepository empresaRepo;

    public boolean existeEmpresa(String username, String correo) {
        return empresaRepository.findByUsernameOrCorreoElectronico(username, correo) != null;
    }

    public Empresa guardarEmpresa(Empresa empresa) {
        empresa.setPassword(PasswordEncoderUtil.encode(empresa.getPassword()));
        return empresaRepository.save(empresa);
    }

    public Empresa actualizarEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa obtenerEmpresaPorId(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    public List<String> getSectoresUnicos() {
        return empresaRepository.findAll()
            .stream()
            .map(Empresa::getSector)
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .distinct()
            .collect(Collectors.toList());
    }
    
    @Transactional
    public void eliminarEmpresaConRelaciones(Long empresaId) {
        ofertaRepo.deleteByEmpresaId(empresaId);
        notificacionRepo.deleteByEmpresaId(empresaId);
        invitacionRepo.deleteByEmpresaId(empresaId);
        empresaRepo.deleteById(empresaId);
    }
    
    

}