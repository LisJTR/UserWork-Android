package com.TFG_backend.dockerized.postgresql.oferta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="oferta")
public class Oferta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descripcion;
	private String aptitudes;
	private String que_se_ofrece;
	private Long empresa_id;
	private boolean publicidad;
	private String fecha_publicacion;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAptitudes() {
		return aptitudes;
	}
	public void setAptitudes(String aptitudes) {
		this.aptitudes = aptitudes;
	}
	public String getQue_se_ofrece() {
		return que_se_ofrece;
	}
	public void setQue_se_ofrece(String que_se_ofrece) {
		this.que_se_ofrece = que_se_ofrece;
	}
	public Long getEmpresa_id() {
		return empresa_id;
	}
	public void setEmpresa_id(Long empresa_id) {
		this.empresa_id = empresa_id;
	}
	public boolean isPublicidd() {
		return publicidad;
	}
	public void setPublicidd(boolean publicidd) {
		this.publicidad = publicidd;
	}
	public String getFecha_publicacion() {
		return fecha_publicacion;
	}
	public void setFecha_publicacion(String fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}
	@Override
	public String toString() {
		return "Oferta [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", aptitudes=" + aptitudes
				+ ", que_se_ofrece=" + que_se_ofrece + ", empresa_id=" + empresa_id + ", publicidd=" + publicidad
				+ ", fecha_publicacion=" + fecha_publicacion + "]";
	}
	
	
	

}
