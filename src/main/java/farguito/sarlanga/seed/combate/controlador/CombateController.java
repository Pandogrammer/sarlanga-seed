package farguito.sarlanga.seed.combate.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
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
import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.acciones.FabricaDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.EstadoDeCombate;
import farguito.sarlanga.seed.combate.PersonajeDeCombate;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.criaturas.Criaturas;
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
					acciones.agregarNodo(combate.getPersonajeActivo().getAcciones().get(i).getAccion());
				respuesta.agregar("acciones", acciones);
				
				Respuesta objetivos = new Respuesta();
				Respuesta aliados = new Respuesta();
				Respuesta enemigos = new Respuesta();
				for(int i = 0; i < combate.getPersonajes().size(); i++) {
					PersonajeDeCombate pj = combate.getPersonajes().get(i); 
					String nodo = "["+pj.getId()+"]: "+pj.getNombre()+" : "+pj.getVida()+"/"+pj.getVidaMax();
					if(pj instanceof Aliado)
						aliados.agregarNodo(nodo);
					else 
						enemigos.agregarNodo(nodo);
				}

				objetivos.agregar("aliados", aliados);
				objetivos.agregar("enemigos", enemigos);
				
				respuesta.agregar("objetivos", objetivos);
				
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
			
			int esenciaTotal = 0;
			int esenciaMax = niveles.get(nivel).getEsencia();
			
			for(PersonajeDeCombateDTO pj : pjs){
				Personaje pjPersonaje = this.fabCriaturas.crear(pj.getCriatura());
				esenciaTotal += pjPersonaje.getEsencia();
				
				List<Accion> pjAcciones = new ArrayList<>();			
				for(Acciones a : pj.getAcciones()) {
					Accion ac = fabAcciones.crear(a);
					esenciaTotal += ac.getEsencia();
					pjAcciones.add(ac);
				}

				personajes.add(new Aliado(pjPersonaje, pjAcciones));
			}
			if(esenciaTotal <= esenciaMax) {
				combate = new SistemaDeCombate();
				combate.iniciar(personajes, crearEnemigos(nivel));		
	
				respuesta.agregarMensaje("combate iniciado");
			} else {
				respuesta.agregarMensaje("la cantidad de esencia es mayor a la del nivel");
				respuesta.agregarMensaje("esencia ingresada: "+esenciaTotal);
				respuesta.agregarMensaje("esencia del nivel: "+esenciaMax);
			}
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	@GetMapping("creacion-nivel")
	public Respuesta creacionNivel() {
		Respuesta respuesta = new Respuesta();
		try {
			
			Respuesta criaturas = new Respuesta();
			List<Criaturas> tiposDeCriaturas = new ArrayList<Criaturas>(Arrays.asList(Criaturas.values()));
			for(Criaturas criatura : tiposDeCriaturas) {
				criaturas.agregarNodo(this.fabCriaturas.crear(criatura));
			}

			Respuesta acciones = new Respuesta();
			List<Acciones> tiposDeAcciones = new ArrayList<Acciones>(Arrays.asList(Acciones.values()));
			for(Acciones accion : tiposDeAcciones) {
				acciones.agregarNodo(this.fabAcciones.crear(accion));
			}
			
			respuesta.agregar("criaturas", criaturas);
			respuesta.agregar("acciones", acciones);
			
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
			else
				respuesta.agregarMensaje("no pasaste de nivel, que flasheá");
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}	
	
	
	@GetMapping("mensajes-combate")
	public Respuesta mensajesCombate(){
		Respuesta respuesta = new Respuesta();
		try {
			for(String mensaje : combate.getMensajes())
				respuesta.agregarMensaje(mensaje);
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
