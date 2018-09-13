package farguito.sarlanga.seed.niveles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.combate.controlador.PersonajeDeCombateDTO;

@CrossOrigin
@RestController
@RequestMapping(value = "nivel", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControladorDeNiveles {

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private RepositorioDeNiveles niveles;
	

	@GetMapping
	public Respuesta listar(@RequestParam(required = false) Integer id) {
		Respuesta respuesta = new Respuesta();
		try {
			if (id != null) {
				respuesta.agregar(id.toString(), new NivelDTO(niveles.get(id)));
			} else {
				niveles.getAll().entrySet().stream().forEach(n -> {
					respuesta.agregar(n.getKey().toString(), new NivelDTO(n.getValue()));
				});	
			}
		} catch (Exception e) {
			respuesta.agregar("error", e.getMessage());
			e.printStackTrace();
		}
		return respuesta;		
	}
	
	@PutMapping("{id}")
	public Respuesta pisar(@PathVariable Integer id
						 , @RequestBody List<PersonajeDeCombateDTO> pjs){
		Respuesta respuesta = new Respuesta();
		try {
			niveles.pisar(id, niveles.conversor(pjs));
			respuesta.exito();
		} catch (Exception e) {
			respuesta.agregar("error", e.getMessage());
			e.printStackTrace();
		}
		return respuesta;	
	}
	
	
	@PostMapping
	public Respuesta agregar(@RequestBody Integer esencia
						   , @RequestBody List<PersonajeDeCombateDTO> pjs){
		Respuesta respuesta = new Respuesta();
		try {
			niveles.agregar(esencia, niveles.conversor(pjs));
			respuesta.exito();
		} catch (Exception e) {
			respuesta.agregar("error", e.getMessage());
			e.printStackTrace();
		}
		return respuesta;	
	}
	

}
