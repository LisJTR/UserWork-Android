package com.TFG_backend.dockerized.postgresql.user;

//recibe datos del frontend
public class LoginRequest {

	  private String username;
	    private String correo_electronico;
	    private String password;

	    public LoginRequest() {
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getCorreo_electronico() {
	        return correo_electronico;
	    }

	    public void setCorreo_electronico(String correo_electronico) {
	        this.correo_electronico = correo_electronico;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
	}