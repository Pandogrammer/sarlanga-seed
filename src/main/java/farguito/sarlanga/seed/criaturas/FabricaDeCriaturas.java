package farguito.sarlanga.seed.criaturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class FabricaDeCriaturas {
	
	@Autowired
	private RepositorioDeCriaturas repositorio;
	
	public FabricaDeCriaturas() {}
	
	public Personaje crear(Criaturas raza) {
		Personaje p = repositorio.obtener(raza);
		switch(p.getRaza()) {
		case RATA: return new Rata(p); 
		case SLIME: return new Slime(p);
		case IMP: return new Imp(p);
		case GOLEM: return new Golem(p); 
		case QUIMERA: return new Quimera(p); 
		default: return null;
		}
	}
		

}
