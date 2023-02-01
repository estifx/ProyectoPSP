
public class Usuario {
	
	private String nombre;
	private String contrasena;
	private String email;
	private String telefono;
	
	public Usuario(String nombre, String contrasena, String email,String telefono) {

		this.nombre = nombre;
		this.contrasena = contrasena;
		this.email = email;
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", contrasena=" + contrasena
				+ ", email=" + email + ", email="+ telefono +"]";
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
	
	
}
