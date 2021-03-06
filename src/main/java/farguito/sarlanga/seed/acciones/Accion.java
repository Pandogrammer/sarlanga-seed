package farguito.sarlanga.seed.acciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public abstract class Accion {
	
	protected Acciones accion;
	protected int esencia;
	protected int cansancio;
	protected List<TiposDeAccion> tipos = new ArrayList<>();
	protected List<Map> efectos = new ArrayList<>(); //a definir bien
	protected boolean melee;
	protected PersonajeDeCombate origen;
	
	public abstract Map ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino);

	public List<TiposDeAccion> getTipos() {
		return tipos;
	}

	public int getCansancio() {
		return cansancio;
	}

	public Acciones getAccion() {
		return accion;
	}

	public int getEsencia() {
		return esencia;
	}

	public boolean isMelee() {
		return melee;
	}
	
	
	
	
}
