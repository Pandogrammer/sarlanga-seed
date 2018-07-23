package farguito.sarlanga.seed.combate;

import java.util.List;

import farguito.sarlanga.seed.estrategias.EstrategiaDeCombate;

public class Enemigo extends PersonajeDeCombate {
	
	EstrategiaDeCombate estrategia;
	
	public void decidir(List<PersonajeDeCombate> personajes) {
		estrategia.accionar(personajes);				
	}

	public EstrategiaDeCombate getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(EstrategiaDeCombate estrategia) {
		this.estrategia = estrategia;
	}
	
	

}
