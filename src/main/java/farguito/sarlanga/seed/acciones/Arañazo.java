package farguito.sarlanga.seed.acciones;

import java.util.HashMap;
import java.util.Map;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class Arañazo extends Accion {

	private int daño;
	private float multiplicador = 0.6f;

	public Arañazo() {
		this.accion = Acciones.ARAÑAZO;
		this.esencia = 0;
		this.cansancio = 65;
		
		tipos.add(TiposDeAccion.OFENSIVO);
	}
	
	public Map ejecutar(PersonajeDeCombate origen, PersonajeDeCombate destino) {
		daño = Math.round(origen.getAtaque() * multiplicador);
		
		destino.dañar(daño);
		origen.cansar(cansancio);
				
		String mensaje = origen.getNombre()+" arañó a "+destino.getNombre()+" causando "+daño+" de daño";

		Map<String, Object> resultado = new HashMap();
		
		resultado.put("origen", origen.getId());
		resultado.put("destino", destino.getId());
		resultado.put("accion", this.accion);
		resultado.put("daño", daño);
		resultado.put("mensaje", mensaje);
		
		return resultado;
	}

}
