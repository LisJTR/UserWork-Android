package com.TFG_backend.dockerized.postgresql.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="alumno")
public class Alumno {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String titulacion;
    private String direccion;
    private String ciudad;
    private String telefono;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    private String centro;
    private String descripcion;
    private String aptitudes;
    private String foto;
    private String curriculum;
    private String verificacion_titulacion;
    
    
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
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	
	public String getTitulacion() {
		return titulacion;
	}
	
	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCorreoElectronico() {
	    return correoElectronico;
	}
	
	public void setCorreoElectronico(String correoElectronico) {
	    this.correoElectronico = correoElectronico;
	}
	
	public String getCentro() {
		return centro;
	}
	
	public void setCentro(String centro) {
		this.centro = centro;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getVerificacion_titulacion() {
		return verificacion_titulacion;
	}

	public void setVerificacion_titulacion(String verificacion_titulacion) {
		this.verificacion_titulacion = verificacion_titulacion;
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", username=" + username
				+ ", password=" + password + ", titulacion=" + titulacion + ", direccion=" + direccion + ", ciudad="
				+ ciudad + ", telefono=" + telefono + ", correo_electronico=" + correoElectronico + ", centro="
				+ centro + ", descripcion=" + descripcion + ", aptitudes=" + aptitudes + ", foto=" + foto
				+ ", curriculum=" + curriculum + ", verificacion_titulacion=" + verificacion_titulacion + "]";
	}
	
	
    
    
	
}