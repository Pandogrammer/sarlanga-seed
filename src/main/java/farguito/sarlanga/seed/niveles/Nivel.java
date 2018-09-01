package farguito.sarlanga.seed.niveles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.estrategias.Agresivo;
import farguito.sarlanga.seed.estrategias.Defensivo;
import farguito.sarlanga.seed.estrategias.EstrategiaDeCombate;

public class Nivel {
	
	private Integer id;
	private Integer siguienteNivel;
	private Integer esencia;
	
	private List<PersonajeDeCombate> personajes = new ArrayList<>();
	
	public Nivel(int esencia, List<PersonajeDeCombate> personajes) {
		this.esencia = esencia;
		this.personajes = personajes;
	}
	
	private EstrategiaDeCombate crearIA() {
		Random r = new Random(System.currentTimeMillis());
		
		switch(r.nextInt(2)) {
		case 0 : return new Agresivo(); 
		case 1 : return new Defensivo();
		default: return null;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiguienteNivel() {
		return siguienteNivel;
	}

	public void setSiguienteNivel(Integer siguienteNivel) {
		this.siguienteNivel = siguienteNivel;
	}

	public Integer getEsencia() {
		return esencia;
	}

	public void setEsencia(Integer esencia) {
		this.esencia = esencia;
	}

	public List<Enemigo> getEnemigos() {
		List<Enemigo> enemigos = new ArrayList<>();
		personajes.stream().forEach(pj -> {
			enemigos.add(
					new Enemigo(
							pj.getPosicion()
						  , pj.getPjBase()
						  , pj.getAcciones()
						  , crearIA()));
		});
		return enemigos;
	}

	public List<PersonajeDeCombate> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(List<PersonajeDeCombate> personajes) {
		this.personajes = personajes;
	}
	
	

	
	
}
