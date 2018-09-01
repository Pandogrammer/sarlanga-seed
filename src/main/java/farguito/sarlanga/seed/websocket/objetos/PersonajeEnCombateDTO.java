package farguito.sarlanga.seed.websocket.objetos;

import java.util.ArrayList;
import java.util.List;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.criaturas.Criaturas;

public class PersonajeEnCombateDTO {

	private Integer id;
	private Integer posicion;
	private Criaturas criatura;
	private List<Acciones> acciones = new ArrayList<>();
	
			
	public PersonajeEnCombateDTO(PersonajeDeCombate personaje) {
		this.id = personaje.getId();
		this.posicion = personaje.getPosicion();
		this.criatura = personaje.getPjBase().getRaza();
		personaje.getAcciones().stream().forEach(a -> {
			this.acciones.add(a.getAccion());
		});
		
	}	
	

	public Integer getId() {
		return id;
	}
	
	public Integer getPosicion() {
		return posicion;
	}

	public Criaturas getCriatura() {
		return criatura;
	}

	public List<Acciones> getAcciones() {
		return acciones;
	}
	
	
	
	
}
