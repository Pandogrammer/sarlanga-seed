package farguito.sarlanga.seed.combate;

public abstract class PersonajeDeCombate {
	
	int vida;
	int mana;
	int velocidad;
	int enfriamiento = 0;
	private int daño;
	boolean vivo = true;
	
	public void dañar(int daño) {
		vida += daño;
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

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
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

	
	
}
