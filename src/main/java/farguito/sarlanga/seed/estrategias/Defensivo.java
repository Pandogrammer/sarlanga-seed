package farguito.sarlanga.seed.estrategias;

import java.util.List;

import farguito.sarlanga.seed.acciones.Golpe;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;

//ataca al objetivo con mas vida con ataques mas poderosos
public class Defensivo extends EstrategiaDeCombate {
	
	public void preparar(List<PersonajeDeCombate> personajes) {
		//preparo el objetivo
		int i = 0;
		PersonajeDeCombate objetivo = null;
		
		while(i < personajes.size()) {
			if(personajes.get(i) instanceof Aliado && personajes.get(i).isVivo()) {
				//si no tiene un objetivo, o si tiene mas vida
				if(objetivo == null || (personajes.get(i).getVida() > objetivo.getVida()))
					objetivo = personajes.get(i);
			}
			
			i++;			
		}
		
		//preparo la accion		
		//for(getOrigen().get)
		this.setAccion(new Golpe());
		this.setObjetivo(objetivo);
	}
	
}
