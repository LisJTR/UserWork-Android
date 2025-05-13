package com.TFG_backend.dockerized.postgresql.aplicacion;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aplicacion_oferta")
public class AplicacionOferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("alumno_id")
    @Column(name = "alumno_id")
    private Long alumnoId;

    @JsonProperty("oferta_id")
    @Column(name = "oferta_id")
    private Long ofertaId;

    @Column(name = "fecha_aplicacion")
    private LocalDateTime fechaAplicacion;

    private String estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public Long getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Long ofertaId) {
		this.ofertaId = ofertaId;
	}

	public LocalDateTime getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(LocalDateTime fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "AplicacionOferta [id=" + id + ", alumnoId=" + alumnoId + ", ofertaId=" + ofertaId + ", fechaAplicacion="
				+ fechaAplicacion + ", estado=" + estado + "]";
	}

	
}
