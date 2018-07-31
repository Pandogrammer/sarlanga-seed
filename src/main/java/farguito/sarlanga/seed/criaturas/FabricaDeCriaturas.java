package farguito.sarlanga.seed.criaturas;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class FabricaDeCriaturas {
	
	public FabricaDeCriaturas() {}
	
	public Personaje crear(Criaturas p) {
		switch(p) {
		case RATA: return new Rata(); 
		case GOLEM: return new Golem(); 
		default: return null;
		}
	}
		

}
