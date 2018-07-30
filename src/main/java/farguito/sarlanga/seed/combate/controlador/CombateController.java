package farguito.sarlanga.seed.combate.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.acciones.Arañazo;
import farguito.sarlanga.seed.acciones.Golpe;
import farguito.sarlanga.seed.acciones.RepositorioDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.criaturas.RepositorioDePersonajes;
import farguito.sarlanga.seed.estrategias.Agresivo;

@RestController
@SessionScope
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CombateController {

	@Autowired
	private RepositorioDePersonajes personajes;
	
	private RepositorioDeAcciones acciones;
	
	private SistemaDeCombate combate;
	
	
	@PostConstruct
	private void init() {
		personajes.save(new Personaje("Rata", 30, 10, 10, 1));
		personajes.save(new Personaje("Golem", 50, 6, 15, 1));
		
		acciones = new RepositorioDeAcciones();
		
		acciones.save(new Arañazo());
		acciones.save(new Golpe());
		
	}
	
	@GetMapping("partida")
	public Respuesta personajes(){
		Respuesta respuesta = new Respuesta();
		try {
			List<PersonajeDTO> aliados = new ArrayList<>();
			List<PersonajeDTO> enemigos = new ArrayList<>();
			
			combate.getPersonajes().stream().forEach(p -> {
				if(p instanceof Aliado) {
					aliados.add(new PersonajeDTO(p));
				} else {
					enemigos.add(new PersonajeDTO(p));
				}
			});
			
			respuesta.agregar("estado", combate.getEstado());
			if(combate.isTurnoJugador())
				respuesta.agregar("acciones", combate.getPersonajeActivo().getAcciones()); //TODO: lista de acciones
			respuesta.agregar("personajeActivo", combate.getPersonajeActivo().getId());
			respuesta.agregar("aliados", aliados);
			respuesta.agregar("enemigos", enemigos);
			
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
	
	@PostMapping("accionar")
	public Respuesta accionar(
			//@RequestBody accion/id?
			@RequestBody int objetivoId
			) {

		Respuesta respuesta = new Respuesta();
		try {
			String mensaje = combate.accionar(objetivoId, new Golpe());
			respuesta.agregarMensaje(mensaje);
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}
	
			
	
	@PostMapping("iniciar")
	public Respuesta iniciar(
			@RequestBody List<PersonajePreCombateDTO> personajes,
			@RequestParam Long nivel
			) {
		Respuesta respuesta = new Respuesta();
		try {
			List<Aliado> aliados = new ArrayList<>();
			
			personajes.stream().forEach(p -> {
				this.personajes.findById(p.getIdPersonaje());
				
				aliados.add(new Aliado(
						this.personajes.findById(p.getIdPersonaje()).get()
					  , this.acciones.findById(p.getIdAcciones())));
			});
			
			combate = new SistemaDeCombate(aliados, buscarEnemigos(nivel));		
	
			combate.start();
			respuesta.agregarMensaje("iniciar");
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	@PostMapping("pausar")
	public Respuesta pausar() {
		Respuesta respuesta = new Respuesta();
		try {
			combate.pausar();
			respuesta.agregarMensaje("pausar");
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}

	
	@PostMapping("seguir")
	public Respuesta seguir() {
		Respuesta respuesta = new Respuesta();
		try {
			combate.seguir();
			respuesta.agregarMensaje("seguir");
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}	
	
	
	
	
	
	
	
	
	//combate service
	private List<Enemigo> buscarEnemigos(Long nivel) {
		//nivel repository
		List<Enemigo> enemigos = new ArrayList<>();
		List<Integer> accionesId = new ArrayList<>();
		accionesId.add(1);
		
		enemigos.add(new Enemigo(
				personajes.findById(nivel).get()
			  , acciones.findById(accionesId)
			  , new Agresivo()));
		
		
		return enemigos;
	}
	
	
	
	
}
