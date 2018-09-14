package farguito.sarlanga.seed.criaturas;

public class Golem extends Personaje {

	public Golem() {
		super();
		
		this.raza = Criaturas.GOLEM;
		this.esencia = 2;
		
		this.vida = 85;
		this.ataque = 14;
		this.velocidad = 10;		
	}

}
