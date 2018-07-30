package farguito.sarlanga.seed.acciones;

import java.util.ArrayList;
import java.util.List;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public abstract class Accion {
	
	protected List<TipoDeAccion> tipos = new ArrayList<>();
	
	public abstract String ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino);

}
