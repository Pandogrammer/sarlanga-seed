package farguito.sarlanga.seed.niveles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.estrategias.Agresivo;
import farguito.sarlanga.seed.estrategias.EstrategiaDeCombate;

public class Nivel {
	
	private Integer id;
	private Integer siguienteNivel;
	private Integer esencia;
	
	private List<Enemigo> enemigos = new ArrayList<>();
	
	//cuando gana el jugador
	public Nivel(List<PersonajeDeCombate> personajes) {
		personajes.stream().forEach(pj -> {
			enemigos.add(
					new Enemigo(
							pj.getPjBase()
						  , pj.getAcciones()
						  , crearIA()));
		});
	}
	
	private EstrategiaDeCombate crearIA() {
		return new Agresivo();
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
		return enemigos;
	}

	public void setEnemigos(List<Enemigo> enemigos) {
		this.enemigos = enemigos;
	}
	
	
}
