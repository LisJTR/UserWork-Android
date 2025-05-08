package com.TFG_backend.dockerized.postgresql.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="empresa")
public class Empresa {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String nombre;
	 private String username;
	 private String password;
	 private String sector;
	 private String ciudad;
	 private int telefono;
	 @Column(name = "correo_electronico")
	 private String correoElectronico;
	 private String descripcion;
	 private String logo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getcorreoElectronico() {
		return correoElectronico;
	}
	public void setcorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nombre=" + nombre + ", username=" + username + ", password=" + password
				+ ", sector=" + sector + ", ciudad=" + ciudad + ", telefono=" + telefono + ", correo_electronico="
				+ correoElectronico + ", descripcion=" + descripcion + ", logo=" + logo + "]";
	}
	 
	 
	 

}
