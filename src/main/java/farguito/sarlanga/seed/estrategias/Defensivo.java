package farguito.sarlanga.seed.estrategias;

import java.util.List;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.TiposDeAccion;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;

//ataca al objetivo con mas vida con ataques mas poderosos
public class Defensivo extends EstrategiaDeCombate {
	
	public void preparar(List<PersonajeDeCombate> objetivosPosibles) {
		//preparo el objetivo
		int i = 0;
		PersonajeDeCombate objetivo = null;
		
		while(i < objetivosPosibles.size()) {
			if(objetivosPosibles.get(i) instanceof Aliado && objetivosPosibles.get(i).isVivo()) {
				//si no tiene un objetivo, o si tiene mas vida
				if(objetivo == null || (objetivosPosibles.get(i).getVida() > objetivo.getVida()))
					objetivo = objetivosPosibles.get(i);
			}
			
			i++;			
		}
		
		//preparo la accion		
		Accion accionElegida = null;
		for(Accion accion : getOrigen().getAcciones()) {
			if( accion.getTipos().contains(TiposDeAccion.OFENSIVO)
			&& (accionElegida == null || accion.getCansancio() > accionElegida.getCansancio())	) {
					accionElegida = accion;
			}
		}
		this.setAccion(accionElegida);
		this.setObjetivo(objetivo);
	}
	
}
