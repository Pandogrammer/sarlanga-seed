package farguito.sarlanga.seed.criaturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import farguito.sarlanga.seed.Respuesta;

@RestController
@ApplicationScope
@RequestMapping(value = "criaturas", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControladorDeCriaturas {
		
	@Autowired
	private RepositorioDeCriaturas repo;
	
	@GetMapping
	public Respuesta listar() {
		Respuesta respuesta = new Respuesta();
		
		respuesta.agregar("criaturas", repo.listar());
		
		return respuesta;
	}
	
	@GetMapping("editar")
	public Respuesta editar(Personaje p) {
		Respuesta respuesta = new Respuesta();
		
		respuesta.agregar("exito", repo.editar(p));
		
		return respuesta;
	}

}
