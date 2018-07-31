package farguito.sarlanga.seed.combate.controlador;

import java.util.List;

import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.criaturas.Criaturas;

public class PersonajePreCombateDTO {
	
	private Criaturas personaje;
	private List<Acciones> acciones;
	//List<mejoras>
	
	public PersonajePreCombateDTO() {}

	public Criaturas getPersonaje() {
		return personaje;
	}

	public List<Acciones> getAcciones() {
		return acciones;
	}
	
	

}
