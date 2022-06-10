package clases;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public abstract class Usuario {
	
	private String nombre;
	private String apellido;
	private int edad;
	private String dni;
	private String nombreDeUsuario;
	private String contraseņa;
	private Date fechaDeCreacion;
	
	public Usuario(String nombre, String apellido, int edad, String dni, String nombreDeUsuario, String contraseņa) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.dni = dni;
		this.nombreDeUsuario = nombreDeUsuario;
		this.contraseņa = contraseņa;
		fechaDeCreacion = Calendar.getInstance().getTime();
	}

	@Override
	public String toString() {
		return "nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", dni=" + dni
				+ ", nombreDeUsuario=" + nombreDeUsuario + ", contraseņa=" + contraseņa + ", fechaDeCreacion="
				+ fechaDeCreacion;
	}

	public void CambiarContraseņa(String contraseņa) {
		
		this.contraseņa = contraseņa;
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, nombreDeUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		Usuario other = (Usuario) obj;
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (Objects.equals(dni, other.dni) || Objects.equals(nombreDeUsuario, other.nombreDeUsuario))
			return true;
		else
			return false;
		
	}
	
	
	

}
