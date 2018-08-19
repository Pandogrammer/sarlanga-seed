package farguito.sarlanga.seed.niveles;

import java.util.ArrayList;
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
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;

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
		List<PersonajeDeCombate> pjs = new ArrayList<>();
		List<Accion> acciones;
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		pjs.add(new Aliado(fabCriaturas.crear(Criaturas.RATA), acciones));

		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(fabCriaturas.crear(Criaturas.RATA), acciones));
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(fabCriaturas.crear(Criaturas.RATA), acciones));
		
		
		agregar(3, pjs);
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
	
	
	public void agregar(int esencia, List<PersonajeDeCombate> pjs) {
		niveles.put(niveles.size()+1, new Nivel(esencia, pjs));
	}
}
