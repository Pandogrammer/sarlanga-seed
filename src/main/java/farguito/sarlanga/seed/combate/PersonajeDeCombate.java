package farguito.sarlanga.seed.combate;

import java.util.List;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.criaturas.Personaje;

public abstract class PersonajeDeCombate {
	
	private Integer id;
	private String nombre;
	private int vida;
	private int vidaMax;
	private int velocidad;
	private int enfriamiento = 0;
	private int daño;
	private boolean vivo = true;
	private List<Accion> acciones;
	
	public PersonajeDeCombate(Personaje pj, List<Accion> acciones) {
		this.daño = pj.getAtaque();
		this.nombre = pj.getNombre();
		this.vida = pj.getVida();
		this.vidaMax = pj.getVida();
		this.velocidad = pj.getVelocidad();
		this.acciones = acciones;
	}
		
	public void dañar(int daño) {
		vida -= daño;
		if (vida <= 0)
			vivo = false;
	}

	public void descansar() {
		enfriamiento -= velocidad;	
		if (enfriamiento < 0) enfriamiento = 0; 
	}

	public void cansar(int cansancio) {
		enfriamiento += cansancio;
	}
	
	public int getDaño() {
		return daño;
	}

	public void setDaño(int daño) {
		this.daño = daño;
	}

	public int getEnfriamiento() {
		return enfriamiento;
	}

	public void setEnfriamiento(int enfriamiento) {
		this.enfriamiento = enfriamiento;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	
	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVidaMax() {
		return vidaMax;
	}

	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}

	public List<Accion> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	
	
}
