package farguito.sarlanga.seed.criaturas;

public class Golem extends Personaje {

	public Golem() {
		super();
		
		this.raza = Criaturas.GOLEM;
		this.esencia = 2;
		
		this.vida = 70;
		this.ataque = 18;
		this.velocidad = 10;		
	}

}
