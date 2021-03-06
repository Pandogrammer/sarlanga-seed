package farguito.sarlanga.seed.niveles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.acciones.FabricaDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.combate.PersonajeDeCombateDTO;
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.criaturas.Personaje;

@Service
@ApplicationScope
public class RepositorioDeNiveles {

	@Autowired
	private FabricaDeCriaturas fabCriaturas;
	
	@Autowired
	private FabricaDeAcciones fabAcciones;
	
	Map<Integer, Nivel> niveles = new ConcurrentHashMap<>();
	
	
	public RepositorioDeNiveles() {}
	
	
	@PostConstruct
	private void init() {
		
		//nivel 1
		List<PersonajeDeCombate> pjs = new ArrayList<>();
		List<Accion> acciones;
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.RATA), acciones));
		
		agregar(pjs);
		

		
		//nivel 2
		pjs = new ArrayList<>();
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		agregar(pjs);


		
		//nivel 3
		pjs = new ArrayList<>();

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(1, fabCriaturas.crear(Criaturas.RATA), acciones));
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(3, fabCriaturas.crear(Criaturas.RATA), acciones));

		
		
		//nivel 4
		pjs = new ArrayList<>();

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(4, fabCriaturas.crear(Criaturas.RATA), acciones));
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(5, fabCriaturas.crear(Criaturas.RATA), acciones));

		agregar(pjs);
		

		
		//nivel 5
		pjs = new ArrayList<>();

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		acciones.add(fabAcciones.crear(Acciones.ENRAIZAR));
		pjs.add(new Aliado(4, fabCriaturas.crear(Criaturas.GOLEM), acciones));
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		acciones.add(fabAcciones.crear(Acciones.ENRAIZAR));
		pjs.add(new Aliado(5, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		agregar(pjs);

		
		//nivel 6
		pjs = new ArrayList<>();

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.QUIMERA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		acciones.add(fabAcciones.crear(Acciones.ENRAIZAR));
		pjs.add(new Aliado(4, fabCriaturas.crear(Criaturas.RATA), acciones));
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		acciones.add(fabAcciones.crear(Acciones.ENRAIZAR));
		pjs.add(new Aliado(5, fabCriaturas.crear(Criaturas.RATA), acciones));
		

		
		//nivel 7
		pjs = new ArrayList<>();

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(1, fabCriaturas.crear(Criaturas.RATA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.RATA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(3, fabCriaturas.crear(Criaturas.RATA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(4, fabCriaturas.crear(Criaturas.RATA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(5, fabCriaturas.crear(Criaturas.RATA), acciones));

		
		//nivel 8
		pjs = new ArrayList<>();

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(1, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(3, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(4, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(5, fabCriaturas.crear(Criaturas.GOLEM), acciones));

		agregar(pjs);
		
		
		//nivel 9
		pjs = new ArrayList<>();

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(1, fabCriaturas.crear(Criaturas.QUIMERA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(2, fabCriaturas.crear(Criaturas.QUIMERA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(3, fabCriaturas.crear(Criaturas.QUIMERA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(4, fabCriaturas.crear(Criaturas.QUIMERA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(5, fabCriaturas.crear(Criaturas.QUIMERA), acciones));

		agregar(pjs);
	}
	
	public Map<Integer, Nivel> getAll(){
		return niveles;
	}
	
	public Nivel get(Integer id) {
		return niveles.get(id);
	}
	
	
	public void pisarConAliados(Integer id, List<Aliado> aliados) {
		List<PersonajeDeCombate> pjs = new ArrayList<>();		
		aliados.stream().forEach(p -> {
			pjs.add(p);
		});
		
		pisar(id, pjs);
	}
	
	public void pisar(Integer id, List<PersonajeDeCombate> pjs){
		niveles.get(id).setPersonajes(pjs);	
	}
	

	public void agregarDTO(List<PersonajeDeCombateDTO> pjs) {
		this.agregar(this.conversor(pjs));
	}
	
	public void agregar(List<PersonajeDeCombate> pjs) {
		niveles.put(niveles.size()+1, new Nivel(esenciaTotal(pjs), pjs));
	}
	
	public void agregar(Integer esencia, List<PersonajeDeCombate> pjs) {
		niveles.put(niveles.size()+1, new Nivel(esencia, pjs));
	}

	private Integer esenciaTotal(List<PersonajeDeCombate> pjs) {
		Integer esencia = 0;
		
		for(PersonajeDeCombate pj : pjs) {
			esencia += pj.getPjBase().getEsencia();
			
			for(Accion ac : pj.getAcciones()) {
				esencia += ac.getEsencia();
			}
		}
		
		return esencia;
	}
	
	
	//esto no deberia ir aca pero buen, paja.
	public List<PersonajeDeCombate> conversor(List<PersonajeDeCombateDTO> pjs){
		List<PersonajeDeCombate> personajes = new ArrayList<>();
		
		for(PersonajeDeCombateDTO pj : pjs){
			Personaje pjCriatura = this.fabCriaturas.crear(pj.getCriatura());
			
			List<Accion> pjAcciones = new ArrayList<>();			
			for(Acciones a : pj.getAcciones()) {
				Accion ac = fabAcciones.crear(a);
				pjAcciones.add(ac);
			}

			personajes.add(new Aliado(pj.getPosicion(), pjCriatura, pjAcciones));
		}
		
		return personajes;
	}
}
