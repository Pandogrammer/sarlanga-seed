package farguito.sarlanga.seed.acciones;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class Arañazo extends Accion {

	private int daño;
	private float multiplicador = 0.6f;
	private int cansancio = 65;

	public Arañazo() {
		this.accion = Acciones.ARAÑAZO;
		tipos.add(TiposDeAccion.OFENSIVO);
	}
	
	public String ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino) {
		daño = Math.round(origen.getAtaque() * multiplicador);
		
		destino.dañar(daño);
		origen.cansar(cansancio);
		
		return origen.getNombre()+" arañó a "+destino.getNombre()+" causando "+daño+" de daño";
	}

}
