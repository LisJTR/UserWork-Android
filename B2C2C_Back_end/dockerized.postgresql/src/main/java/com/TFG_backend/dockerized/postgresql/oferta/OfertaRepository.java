package com.TFG_backend.dockerized.postgresql.oferta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

	List<Oferta> findByEmpresaId(Long empresaId);
	void deleteByEmpresaId(Long empresaId);

}
