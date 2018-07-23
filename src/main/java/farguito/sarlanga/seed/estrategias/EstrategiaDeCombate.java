package farguito.sarlanga.seed.estrategias;

import java.util.List;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public abstract class EstrategiaDeCombate {
	
	private PersonajeDeCombate objetivo;
	private Accion accion;
	
	public abstract void accionar(List<PersonajeDeCombate> personajes);

	public PersonajeDeCombate getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(PersonajeDeCombate objetivo) {
		this.objetivo = objetivo;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}
		
	
}
