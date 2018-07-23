package farguito.sarlanga.seed.acciones;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class Atacar extends Accion{

	public void ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino) {
		destino.dañar(origen.getDaño());
		origen.cansar(100);
	}

}
