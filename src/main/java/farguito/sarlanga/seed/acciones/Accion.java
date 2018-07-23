package farguito.sarlanga.seed.acciones;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public abstract class Accion {
	
	public abstract void ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino);

}
