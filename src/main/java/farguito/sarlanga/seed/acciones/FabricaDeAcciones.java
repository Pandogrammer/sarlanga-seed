package farguito.sarlanga.seed.acciones;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class FabricaDeAcciones {
	
	public FabricaDeAcciones() {}
	
	public Accion crear(Acciones a) {
		switch(a) {
		case GOLPE: return new Golpe(); 
		case ARAÑAZO: return new Arañazo(); 
		default: return null;
		}
	}
		

}
