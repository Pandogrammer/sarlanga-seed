package farguito.sarlanga.seed.acciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class Golpe extends Accion {

	private int daño;
	private float multiplicador = 1;

	public Golpe() {
		this.accion = Acciones.GOLPE;
		this.melee = true;
		this.esencia = 0;
		this.cansancio = 100;
		
		tipos.add(TiposDeAccion.OFENSIVO);
	}
	
	public Map ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino) {
		daño = Math.round(origen.getAtaque() * multiplicador);
		
		destino.dañar(daño);
		origen.cansar(cansancio);
		
		String mensaje = origen.getNombre()+" golpeó a "+destino.getNombre()+" causando "+daño+" de daño";

		Map<String, Object> resultado = new HashMap();
		
		this.efectos.add(Efectos.daño(daño));
		
		resultado.put("origen", origen.getId());
		resultado.put("destino", destino.getId());
		resultado.put("accion", this.accion);
		resultado.put("melee", this.melee);
		resultado.put("efectos", this.efectos);
		resultado.put("mensaje", mensaje);
		
		return resultado;
	}

}
