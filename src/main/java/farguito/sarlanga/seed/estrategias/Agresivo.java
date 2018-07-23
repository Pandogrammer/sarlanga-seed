package farguito.sarlanga.seed.estrategias;

import java.util.List;

import farguito.sarlanga.seed.acciones.Atacar;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class Agresivo extends EstrategiaDeCombate{

	public void accionar(List<PersonajeDeCombate> personajes) {
		int i = 0;
		PersonajeDeCombate objetivo = null;
		
		while(i < personajes.size()) {
			if(personajes.get(i) instanceof Aliado) {
				if(objetivo == null)
					objetivo = personajes.get(i);
				else if(personajes.get(i).getVida() < objetivo.getVida())
					objetivo = personajes.get(i);
				
				i++;
			}
		}
		
		this.setAccion(new Atacar());
		this.setObjetivo(objetivo);
	}
	
}
