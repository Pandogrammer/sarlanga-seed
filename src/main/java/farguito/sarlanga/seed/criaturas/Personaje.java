package farguito.sarlanga.seed.criaturas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Personaje {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nombre;
	private int vida;
	private int velocidad;
	private int ataque;
	private int nivel;
	
	
	
	public Personaje(String nombre, int vida, int velocidad, int ataque, int nivel) {
		super();
		this.nombre = nombre;
		this.vida = vida;
		this.velocidad = velocidad;
		this.ataque = ataque;
		this.nivel = nivel;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public int getAtaque() {
		return ataque;
	}
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	

}
