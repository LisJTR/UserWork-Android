package com.TFG_backend.dockerized.postgresql.user;

//envia respuesta al frontend
public class LoginResponse {

	  private String mensaje;
	    private Long id;
	    private String tipo;

	    public LoginResponse(String mensaje, Long id, String tipo) {
	        this.mensaje = mensaje;
	        this.id = id;
	        this.tipo = tipo;
	    }

	    public String getMensaje() {
	        return mensaje;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getTipo() {
	        return tipo;
	    }

	    public void setMensaje(String mensaje) {
	        this.mensaje = mensaje;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public void setTipo(String tipo) {
	        this.tipo = tipo;
	    }
	}