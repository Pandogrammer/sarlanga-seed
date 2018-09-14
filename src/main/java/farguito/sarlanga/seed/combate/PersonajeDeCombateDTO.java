package farguito.sarlanga.seed.combate;

import java.util.ArrayList;
import java.util.List;

import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.criaturas.Criaturas;

public class PersonajeDeCombateDTO {
	
	private Integer esencia;
	private Integer posicion;
	private Criaturas criatura;
	private List<Acciones> acciones;
	//List<mejoras>

	public PersonajeDeCombateDTO() {}
	
	public PersonajeDeCombateDTO(PersonajeDeCombate personaje) {
		
		this.acciones = new ArrayList<>();
		
		this.posicion = personaje.getPosicion();
		this.criatura = personaje.getPjBase().getRaza();
		
		this.esencia = 0;
		this.esencia += personaje.getPjBase().getEsencia();
		
		personaje.getAcciones().stream().forEach(a ->{
			this.esencia += a.getEsencia();
			this.acciones.add(a.getAccion());
		});		
		
	}

	public Criaturas getCriatura() {
		return criatura;
	}

	public List<Acciones> getAcciones() {
		return acciones;
	}

	public Integer getEsencia() {
		return esencia;
	}

	public Integer getPosicion() {
		return posicion;
	}
	
	
	
	

}
