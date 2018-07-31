package farguito.sarlanga.seed.acciones;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class Golpe extends Accion {

	private int daño;
	private float multiplicador = 1;
	private int cansancio = 100;

	public Golpe() {
		tipos.add(TipoDeAccion.OFENSIVO);
	}
	
	public String ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino) {
		daño = Math.round(origen.getAtaque() * multiplicador);
		
		destino.dañar(daño);
		origen.cansar(cansancio);
		
		return origen.getNombre()+" golpeó a "+destino.getNombre()+" causando "+daño+" de daño";
	}

}
