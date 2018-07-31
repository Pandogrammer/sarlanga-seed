package farguito.sarlanga.seed.criaturas;

public class Rata extends Personaje {

	public Rata() {
		super();
		
		this.raza = Criaturas.RATA;
		this.nivel = 1;
		
		this.vida = 30;
		this.ataque = 10;
		this.velocidad = 15;		
	}

}
