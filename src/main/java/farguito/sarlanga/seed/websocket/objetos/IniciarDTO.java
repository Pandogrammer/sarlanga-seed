package farguito.sarlanga.seed.websocket.objetos;

import java.util.List;

import farguito.sarlanga.seed.combate.PersonajeDeCombateDTO;

public class IniciarDTO {

	private List<PersonajeDeCombateDTO> pjs;
	private Integer nivel;
	
	public IniciarDTO() {}

	public List<PersonajeDeCombateDTO> getPjs() {
		return pjs;
	}

	public Integer getNivel() {
		return nivel;
	}
	
	
}
