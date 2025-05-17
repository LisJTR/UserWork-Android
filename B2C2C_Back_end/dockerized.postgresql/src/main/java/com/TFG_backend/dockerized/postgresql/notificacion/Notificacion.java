package com.TFG_backend.dockerized.postgresql.notificacion;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // ejemplo: "aplicacion" o "interes"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @JsonProperty("alumno_id")
    @Column(name = "alumno_id")
    private Long alumnoId;

    @JsonProperty("empresa_id")
    @Column(name = "empresa_id")
    private Long empresaId;

    @JsonProperty("oferta_id")
    @Column(name = "oferta_id")
    private Long ofertaId;

    @JsonProperty("destinatario_tipo")
    @Column(name = "destinatario_tipo")
    private String destinatarioTipo; // "alumno" o "empresa"

    private Boolean leido = false;
    
    @JsonProperty("estado_respuesta")
    @Column(name = "estado_respuesta")
    private String estadoRespuesta = "pendiente";

    private LocalDateTime fecha = LocalDateTime.now();

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

    public String getDestinatarioTipo() {
        return destinatarioTipo;
    }

    public void setDestinatarioTipo(String destinatarioTipo) {
        this.destinatarioTipo = destinatarioTipo;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public String getEstadoRespuesta() {
        return estadoRespuesta;
    }

    public void setEstadoRespuesta(String estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", tipo=" + tipo + ", mensaje=" + mensaje + ", alumnoId=" + alumnoId
				+ ", empresaId=" + empresaId + ", ofertaId=" + ofertaId + ", destinatarioTipo=" + destinatarioTipo
				+ ", leido=" + leido + ", estadoRespuesta=" + estadoRespuesta + ", fecha=" + fecha + "]";
	}
    
    
}