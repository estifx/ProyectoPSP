package programa;

import java.io.Serializable;

public class Usuario implements Serializable{
	private String nombre;
	private String contrasena;
	private String email;
	private String telefono;
	private String dni;
	
	
	
	
	public Usuario(String dni) {
		
		this.dni = dni;
	}

	public Usuario(String dni, String nombre, String email,String contrasena, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
		this.telefono = telefono;	
	}
	
	public Usuario(String email, String contrasena) {
		this.email = email;
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", contrasena=" + contrasena
				+ ", email=" + email + ", email="+ telefono +", dni= "+dni+"]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	

}
