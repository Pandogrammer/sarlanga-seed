package farguito.sarlanga.seed.acciones;

import java.util.ArrayList;
import java.util.List;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public abstract class Accion {
	
	protected int cansancio;
	protected List<TiposDeAccion> tipos = new ArrayList<>();
	protected PersonajeDeCombate origen;
	
	public abstract String ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino);

	public List<TiposDeAccion> getTipos() {
		return tipos;
	}

	public int getCansancio() {
		return cansancio;
	}
	
	
	
}
