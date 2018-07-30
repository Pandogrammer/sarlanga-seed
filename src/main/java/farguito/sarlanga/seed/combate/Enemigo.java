package farguito.sarlanga.seed.combate;

import java.util.List;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.estrategias.EstrategiaDeCombate;

public class Enemigo extends PersonajeDeCombate {
	
	EstrategiaDeCombate estrategia;

	public Enemigo(Personaje pj, EstrategiaDeCombate estrategia, List<Accion> acciones) {
		super(pj, acciones);
		this.estrategia = estrategia;
	}	
	
	
	public EstrategiaDeCombate getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(EstrategiaDeCombate estrategia) {
		this.estrategia = estrategia;
	}
	
	

}