package com.TFG_backend.dockerized.postgresql.user;

public class CambiarPasswordRequest {

	 private Long id;
	 private String tipo; // "alumno" o "empresa"
	 private String passwordActual;
	 private String passwordNueva;
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
	public String getPasswordActual() {
		return passwordActual;
	}
	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}
	public String getPasswordNueva() {
		return passwordNueva;
	}
	public void setPasswordNueva(String passwordNueva) {
		this.passwordNueva = passwordNueva;
	}
	@Override
	public String toString() {
		return "CambiarPasswordRequest [id=" + id + ", tipo=" + tipo + ", passwordActual=" + passwordActual
				+ ", passwordNueva=" + passwordNueva + "]";
	}
	 
	 
}
