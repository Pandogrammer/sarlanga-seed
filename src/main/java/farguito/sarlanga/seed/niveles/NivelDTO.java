package farguito.sarlanga.seed.niveles;

import java.util.ArrayList;
import java.util.List;

import farguito.sarlanga.seed.combate.PersonajeDeCombateDTO;

public class NivelDTO {
	
	private Integer esencia;
	private List<PersonajeDeCombateDTO> personajes;
	
	public NivelDTO(Nivel nivel) {
		this.personajes = new ArrayList<>();
		this.esencia = nivel.getEsencia();
		
		nivel.getPersonajes().stream().forEach(pj -> {
			this.personajes.add(new PersonajeDeCombateDTO(pj));
		});
		
	}
	
	public Integer getEsencia() {
		return esencia;
	}

	public List<PersonajeDeCombateDTO> getPersonajes() {
		return personajes;
	}

	
	

}
