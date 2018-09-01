package farguito.sarlanga.seed.combate;

import java.util.List;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.criaturas.Personaje;

public class Aliado extends PersonajeDeCombate {

	public Aliado(Integer posicion, Personaje pj, List<Accion> acciones) {
		super(posicion, pj, acciones);
	}

}
