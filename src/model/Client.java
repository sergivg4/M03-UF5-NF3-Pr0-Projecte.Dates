/**
 * 
 */
package model;

import java.time.LocalDate;
import java.util.TreeSet;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class Client extends Persona{

	private boolean enviarPublicitat;

	public Client() {
		super();
		enviarPublicitat = false;
	}

	public Client(int idPersona, String dni, String nombre, String apellido, LocalDate fechaDeNacimiento, String email,
			TreeSet<String> telefonos, Adreca direccion, boolean enviarPublicitat) {
		super(idPersona, dni, nombre, apellido, fechaDeNacimiento, email, telefonos, direccion);
		this.enviarPublicitat = enviarPublicitat;
	}

	public boolean isEnviarPublicitat() {
		return enviarPublicitat;
	}

	public void setEnviarPublicitat(boolean enviarPublicitat) {
		this.enviarPublicitat = enviarPublicitat;
	}

	@Override
	public String toString() {
		return "Client [ IdPersona = " + getIdPersona() + ", Dni = " + getDni() + ", Nombre = " + getNombre() + ", Apellido = "
				+ getApellido() + ", Edad = " + getEdad() + ", FechaDeNacimiento = " + getFechaDeNacimiento() + ", Email = " + getEmail()
				+ ", Telefono = " + getTelefonos() + ", enviarPublicitat = " + enviarPublicitat + ", Direccion = " + getDireccion() + " ]";
	}
	
	
	
}
