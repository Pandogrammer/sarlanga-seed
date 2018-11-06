package farguito.sarlanga.seed.criaturas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class RepositorioDeCriaturas {
	
	private Map<String, Personaje> criaturas;
	
	
	@PostConstruct
	private void init() {
		criaturas = new HashMap<>();

		criaturas.put("RATA", new Personaje(Criaturas.RATA, 30, 12, 10, 1));
		criaturas.put("SLIME", new Personaje(Criaturas.SLIME, 40, 6, 12, 1));

		criaturas.put("IMP", new Personaje(Criaturas.IMP, 40, 14, 12, 2));
		criaturas.put("GOLEM", new Personaje(Criaturas.GOLEM, 60, 8, 14, 2));
		
		criaturas.put("QUIMERA", new Personaje(Criaturas.QUIMERA, 50, 10, 20, 3));
		
	}
	
	public boolean editar(Personaje p) {
		if (criaturas.containsKey(p.getRaza().name())) {
			criaturas.put(p.getRaza().name(), p);
			return true;
		} else {
			return false;
		}		
	}
	
	public Personaje obtener(Criaturas raza) {
		return criaturas.get(raza.name());
	}
	
	public Collection<Personaje> listar(){
		return criaturas.values();
	}
	

}
