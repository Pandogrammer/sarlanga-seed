package farguito.sarlanga.seed.acciones;

import java.util.HashMap;
import java.util.Map;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class Enraizar extends Accion {

	private int daño;
	private int enlentecer = 40;
	private float multiplicador = 0.5f;

	public Enraizar() {
		this.accion = Acciones.ENRAIZAR;
		this.esencia = 1;
		this.cansancio = 120;
		
		tipos.add(TiposDeAccion.UTILIDAD);
	}
	
	public Map ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino) {
		daño = Math.round(origen.getAtaque() * multiplicador);
		
		destino.dañar(daño);
		destino.cansar(enlentecer);
		origen.cansar(cansancio);
		
		String mensaje = origen.getNombre()+" golpeó a "+destino.getNombre()+" causando "+daño+" de daño";

		Map<String, Object> resultado = new HashMap();
		
		this.efectos.add(Efectos.daño(daño));
		this.efectos.add(Efectos.enlentecer(enlentecer));
		
		resultado.put("origen", origen.getId());
		resultado.put("destino", destino.getId());
		resultado.put("accion", this.accion);
		resultado.put("melee", this.melee);
		resultado.put("efectos", this.efectos);
		resultado.put("mensaje", mensaje);
		
		return resultado;
	}

}
