package farguito.sarlanga.seed.combate.controlador;

import java.util.List;

public class PersonajePreCombateDTO {
	
	private Long idPersonaje;
	private List<Integer> idAcciones;
	//List<mejoras>
	
	public PersonajePreCombateDTO() {}
	
	public Long getIdPersonaje() {
		return idPersonaje;
	}
	public void setIdPersonaje(Long idPersonaje) {
		this.idPersonaje = idPersonaje;
	}
	public List<Integer> getIdAcciones() {
		return idAcciones;
	}
	public void setIdAcciones(List<Integer> idAcciones) {
		this.idAcciones = idAcciones;
	}
	
	

}
