package farguito.sarlanga.seed.estrategias;

import java.util.List;

import farguito.sarlanga.seed.acciones.Golpe;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;

//ataca al objetivo con menos vida
public class Agresivo extends EstrategiaDeCombate {

	public void preparar(List<PersonajeDeCombate> personajes) {
		int i = 0;
		PersonajeDeCombate objetivo = null;
		
		while(i < personajes.size()) {
			if(personajes.get(i) instanceof Aliado && personajes.get(i).isVivo()) {
				//si no tiene un objetivo, o si tiene menos vida
				if(objetivo == null || (personajes.get(i).getVida() < objetivo.getVida()))
					objetivo = personajes.get(i);
			}
			
			i++;			
		}
		
		this.setAccion(new Golpe());
		this.setObjetivo(objetivo);
	}
	
}
