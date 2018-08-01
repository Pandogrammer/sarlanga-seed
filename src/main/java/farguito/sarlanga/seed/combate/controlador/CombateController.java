package farguito.sarlanga.seed.combate.controlador;

import java.util.ArrayList;
import java.util.List;

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
import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.FabricaDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.EstadoDeCombate;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.niveles.RepositorioDeNiveles;

@RestController
@SessionScope
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CombateController {

	@Autowired
	private FabricaDeCriaturas fabCriaturas;
	
	@Autowired
	private FabricaDeAcciones fabAcciones;
	
	@Autowired
	private RepositorioDeNiveles niveles;
	
	
	private SistemaDeCombate combate;

	private List<Aliado> personajes;
	private Integer nivelElegido;
	
			
	
	@GetMapping("acciones-posibles")
	public Respuesta accionesPosibles() {

		Respuesta respuesta = new Respuesta();
		try {
			if(combate.isTurnoJugador()) {
				respuesta.agregar("personaje",  combate.getPersonajeActivo().getPjBase().getRaza());
				Respuesta acciones = new Respuesta();
				for(int i = 0; i < combate.getPersonajeActivo().getAcciones().size(); i++)
					acciones.agregar(""+i, combate.getPersonajeActivo().getAcciones().get(i).getAccion());
				respuesta.agregar("acciones", acciones);
			} else {
				respuesta.agregarMensaje("no es tu turno");
			}
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;		
	}
	
	
	@PostMapping("accionar")
	public Respuesta accionar( 
			@RequestParam int accionId,
			@RequestParam int objetivoId
			) {

		Respuesta respuesta = new Respuesta();
		try {
			String mensaje = combate.accionar(objetivoId, combate.getPersonajeActivo().getAcciones().get(accionId));
			respuesta.agregarMensaje(mensaje);
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}
	
			
	
	@PostMapping("iniciar")
	public Respuesta iniciar(
			@RequestBody List<PersonajeDeCombateDTO> pjs,
			@RequestParam Integer nivel
			) {
		Respuesta respuesta = new Respuesta();
		try {
			this.personajes = new ArrayList<>();
			this.nivelElegido = nivel;
			
			pjs.stream().forEach(pj -> {
				Personaje pjPersonaje = this.fabCriaturas.crear(pj.getCriatura());
				List<Accion> pjAcciones = new ArrayList<>();
				
				pj.getAcciones().stream().forEach(a -> {
					pjAcciones.add(fabAcciones.crear(a));
				});

				personajes.add(new Aliado(pjPersonaje, pjAcciones));
			});
			
			combate = new SistemaDeCombate(personajes, crearEnemigos(nivel));		
	
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
	
	
	
	
	@GetMapping("pasar-nivel")
	public Respuesta pasarNivel() {
		Respuesta respuesta = new Respuesta();
		try {
			if(combate.getEstado() == EstadoDeCombate.VICTORIA)
				niveles.pisarConAliados(nivelElegido, personajes);
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}	
	
	
	
	
	//combate service
	private List<Enemigo> crearEnemigos(Integer nivel) {
		return niveles.get(nivel).getEnemigos();
	}
	
	
	
	
	
	
}
