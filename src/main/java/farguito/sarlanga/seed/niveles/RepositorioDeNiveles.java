package farguito.sarlanga.seed.niveles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	Map<Integer, Nivel> niveles = new HashMap<>();
	
	@PostConstruct
	private void init() {
		List<PersonajeDeCombate> pjs = new ArrayList<>();
		
		List<Accion> acciones = new ArrayList<>();		
		acciones.add(fabAcciones.crear(Acciones.GOLPE));
		pjs.add(new Aliado(fabCriaturas.crear(Criaturas.RATA), acciones));
		
		acciones = new ArrayList<>();
		acciones.add(fabAcciones.crear(Acciones.ARAÑAZO));
		pjs.add(new Aliado(fabCriaturas.crear(Criaturas.RATA), acciones));
		
		
		niveles.put(niveles.size(), new Nivel(pjs));
	}
	
	public Nivel get(Integer id) {
		return niveles.get(id);
	}
}
