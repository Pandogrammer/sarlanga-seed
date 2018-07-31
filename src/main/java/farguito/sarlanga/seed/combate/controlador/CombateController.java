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
import farguito.sarlanga.seed.acciones.Golpe;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.estrategias.Agresivo;
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
	
		
	@GetMapping("partida")
	public Respuesta personajes(){
		Respuesta respuesta = new Respuesta();
		try {
			List<PersonajeDeCombate> aliados = new ArrayList<>();
			List<PersonajeDeCombate> enemigos = new ArrayList<>();
			
			combate.getPersonajes().stream().forEach(p -> {
				if(p instanceof Aliado) {
					aliados.add(p);
				} else {
					enemigos.add(p);
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
			@RequestBody List<PersonajePreCombateDTO> pjs,
			@RequestParam Integer nivel
			) {
		Respuesta respuesta = new Respuesta();
		try {
			List<Aliado> aliados = new ArrayList<>();
			
			pjs.stream().forEach(pj -> {
				
				Personaje pjPersonaje = this.fabCriaturas.crear(pj.getCriatura());
				List<Accion> pjAcciones = new ArrayList<>();
				
				pj.getAcciones().stream().forEach(a -> {
					pjAcciones.add(fabAcciones.crear(a));
				});
				
				
				aliados.add(new Aliado(pjPersonaje, pjAcciones));
			});
			
			combate = new SistemaDeCombate(aliados, crearEnemigos(nivel));		
	
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
	private List<Enemigo> crearEnemigos(Integer nivel) {
		return niveles.get(nivel).getEnemigos();
	}
	
	
	
	
}
