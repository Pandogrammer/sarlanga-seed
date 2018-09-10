package farguito.sarlanga.seed.combate;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.Personaje;

@JsonInclude(Include.NON_NULL)
public abstract class PersonajeDeCombate {
	
	private Integer id;
	private Integer posicion;
	private Personaje pjBase;
	private int vida;
	private int vidaMax;
	private int velocidad;
	private int enfriamiento = 0;
	private int ataque;
	private boolean vivo = true;
	private List<Accion> acciones;
	
	public PersonajeDeCombate(Integer posicion, Personaje pj, List<Accion> acciones) {
		this.posicion = posicion;
		this.pjBase = pj;
		this.ataque = pj.getAtaque();		
		this.vida = pj.getVida();
		this.vidaMax = pj.getVida();
		this.velocidad = pj.getVelocidad();
		this.acciones = acciones;
	}
		
	public void dañar(int daño) {
		if (vivo) {
			vida -= daño;
			if (vida <= 0) {
				vivo = false;
				vida = 0;
			}
		} else {
			//contadores de muerte? si sigo pegandole a algo que esta muerto, que pasa?
		}
	}

	public void descansar() {
		enfriamiento -= velocidad;	
		if (enfriamiento < 0) enfriamiento = 0; 
	}

	public void cansar(int cansancio) {
		enfriamiento += cansancio;
	}
	
	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
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
		return pjBase.getRaza().toString();
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

	public Personaje getPjBase() {
		return pjBase;
	}

	public Integer getPosicion() {
		return posicion;
	}
	
	
	
}
