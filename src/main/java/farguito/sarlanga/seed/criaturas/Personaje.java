package farguito.sarlanga.seed.criaturas;

public class Personaje {
	
	protected Criaturas raza;
	protected int vida;
	protected int velocidad;
	protected int ataque;
	protected int nivel;	
	
	public Personaje() {}
	
	
	
	public Criaturas getRaza() {
		return raza;
	}
	public void setRaza(Criaturas raza) {
		this.raza = raza;
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
