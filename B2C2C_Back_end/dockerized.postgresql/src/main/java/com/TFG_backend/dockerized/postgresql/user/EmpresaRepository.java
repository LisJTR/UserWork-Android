package com.TFG_backend.dockerized.postgresql.user;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	Empresa findByUsernameOrCorreoElectronico(String username, String correoElectronico);

}
