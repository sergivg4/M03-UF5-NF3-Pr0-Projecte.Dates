/**
 * 
 */
package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.TreeSet;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public abstract class Persona {

	/******Atributos************/
	private static int numeroPersonas;
	private int idPersona;
	private String dni;
	private String nombre;
	private String apellido;
	private LocalDate fechaDeNacimiento;
	private String email;
	private TreeSet<String> telefonos;
	private Adreca direccion;
	
	/*******Constructores*********/
	protected Persona(){	
		numeroPersonas++;
	}
	
	protected Persona(int idPersona, String dni, String nombre, String apellido) {
		this.idPersona = idPersona;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		numeroPersonas++;
	}

	protected Persona(int idPersona, String dni, String nombre, String apellido, LocalDate fechaDeNacimiento, String email,
			TreeSet<String> telefonos, Adreca direccion) {
		this.idPersona = idPersona;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.email = email;
		this.telefonos = telefonos;
		this.direccion = direccion;
		numeroPersonas++;
	}

	/********Funciones***********/
	
	public int getEdad() {
		return (int)ChronoUnit.YEARS.between(fechaDeNacimiento, LocalDate.now());
	}
	
	public static long diferenciaEdad(Persona p1, Persona p2) {
		return ChronoUnit.YEARS.between(p1.getFechaDeNacimiento(), p2.getFechaDeNacimiento());
	}
	
	/*Getters y setters*/
	
	public static int getNumeroPersonas() {
		return numeroPersonas;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	protected String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TreeSet<String> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(TreeSet<String> telefonos) {
		this.telefonos = telefonos;
	}

	public Adreca getDireccion() {
		return direccion;
	}

	public void setDireccion(Adreca direccion) {
		this.direccion = direccion;
	}

	/****toString*****/
	@Override
	public String toString() {
		return "Persona [ idPersona = " + idPersona + ", dni = " + dni + ", Nombre = " + nombre + ", apellido = " + apellido
				+ ", fechaDeNacimiento = " + fechaDeNacimiento + ", email = " + email + ", telefono = " + telefonos
				+ ", direccion = " + direccion + ", edad = "+getEdad()+" ]";
	}
	
	
	
	
}
