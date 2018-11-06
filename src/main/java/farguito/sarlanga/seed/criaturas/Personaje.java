package farguito.sarlanga.seed.criaturas;

public class Personaje {
	
	protected Criaturas raza;
	protected int vida;
	protected int velocidad;
	protected int ataque;
	protected int esencia;	
	
	public Personaje() {}
	
	public Personaje(Criaturas raza, int vida, int velocidad, int ataque, int esencia) {
		super();
		this.raza = raza;
		this.vida = vida;
		this.velocidad = velocidad;
		this.ataque = ataque;
		this.esencia = esencia;
	}

	public Personaje(Personaje p) {
		this.raza = p.raza;
		this.vida = p.vida;
		this.velocidad = p.velocidad;
		this.ataque = p.ataque;
		this.esencia = p.esencia;
	}	
		
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
	public int getEsencia() {
		return esencia;
	}
	public void setEsencia(int esencia) {
		this.esencia = esencia;
	}	
	

}
