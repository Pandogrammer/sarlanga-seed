package farguito.sarlanga.seed.combate.controlador;

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
