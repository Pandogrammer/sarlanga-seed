package farguito.sarlanga.seed.websocket;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.SarlangaContext;
import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.acciones.FabricaDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.ControladorDeCombate;
import farguito.sarlanga.seed.combate.Enemigo;
import farguito.sarlanga.seed.combate.EstadoDeCombate;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.combate.controlador.PersonajeDeCombateDTO;
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.niveles.RepositorioDeNiveles;
import farguito.sarlanga.seed.websocket.objetos.PersonajeEnCombateDTO;


public class CombateWebSocketController implements ControladorDeCombate {
			
	private FabricaDeCriaturas fabCriaturas;
	
	private FabricaDeAcciones fabAcciones;
	
	private RepositorioDeNiveles niveles;
	
	private SistemaDeCombate combate;
	
	private String sessionId;
	private CombateWebSocketHandler handler;
	private boolean conectado = true;
	
	private List<Aliado> personajes;
	private Integer nivelElegido;
	
	//is this frula? v2.0
	public CombateWebSocketController(String sessionId) {
		this.sessionId = sessionId;
		fabCriaturas = (FabricaDeCriaturas) SarlangaContext.getAppContext().getBean("fabricaDeCriaturas");
		fabAcciones = (FabricaDeAcciones) SarlangaContext.getAppContext().getBean("fabricaDeAcciones");
		niveles = (RepositorioDeNiveles) SarlangaContext.getAppContext().getBean("repositorioDeNiveles");
	}
	
    
	public void accionJugador(int objetivoId, int accionId) {
		enviar("combate", "animar_turno", combate.accionar(objetivoId, combate.getPersonajeActivo().getAcciones().get(accionId)));		
	}
	
	
	public Respuesta iniciar(
			List<PersonajeDeCombateDTO> pjs,
			Integer nivel
			) {
		Respuesta respuesta = new Respuesta();
		try {
			this.personajes = new ArrayList<>();
			this.nivelElegido = nivel;
			
			int esenciaTotal = 0;
			int esenciaMax = niveles.get(nivel).getEsencia();
			
			for(PersonajeDeCombateDTO pj : pjs){
				Personaje pjCriatura = this.fabCriaturas.crear(pj.getCriatura());
				esenciaTotal += pjCriatura.getEsencia();
				
				List<Accion> pjAcciones = new ArrayList<>();			
				for(Acciones a : pj.getAcciones()) {
					Accion ac = fabAcciones.crear(a);
					esenciaTotal += ac.getEsencia();
					pjAcciones.add(ac);
				}

				personajes.add(new Aliado(pj.getPosicion(), pjCriatura, pjAcciones));
			}
			
			if(esenciaTotal <= esenciaMax) {
				combate = new SistemaDeCombate();
				combate.setControlador(this);
				
				List<Enemigo> personajesEnemigos = niveles.get(nivel).crearEnemigos();
				
				combate.preparar(personajes, personajesEnemigos);		

				List<PersonajeEnCombateDTO> aliados = new ArrayList<>();
				List<PersonajeEnCombateDTO> enemigos = new ArrayList<>();
				
				combate.getPersonajes().stream().forEach(pj -> {
					if(pj instanceof Aliado)
						aliados.add(new PersonajeEnCombateDTO(pj));
					else
						enemigos.add(new PersonajeEnCombateDTO(pj));
				});
				
				respuesta.agregar("aliados", aliados);
				respuesta.agregar("enemigos", enemigos);

				combate.iniciar();
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
	
	public Respuesta creacionNivel() {
		Respuesta respuesta = new Respuesta();
		try {
			
			List<Criaturas> tiposDeCriaturas = new ArrayList<Criaturas>(Arrays.asList(Criaturas.values()));
			List<Personaje> criaturas = new ArrayList<>();
			for(Criaturas criatura : tiposDeCriaturas) {
				criaturas.add(this.fabCriaturas.crear(criatura));
			}

			List<Acciones> tiposDeAcciones = new ArrayList<Acciones>(Arrays.asList(Acciones.values()));
			List<Accion> acciones = new ArrayList<>();
			for(Acciones accion : tiposDeAcciones) {
				acciones.add(this.fabAcciones.crear(accion));
			}
			
			respuesta.agregar("criaturas", criaturas);
			respuesta.agregar("acciones", acciones);
			
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}
    
	public Respuesta informacionNivel(Integer nivel) {
		Respuesta respuesta = new Respuesta();
		try {
			int esenciaMax = niveles.get(nivel).getEsencia();
			respuesta.put("esencia", esenciaMax);
		} catch (Exception e) {
			respuesta.error(e);
			e.printStackTrace();
		}
		return respuesta;
	}
    
	//TODO: aca guardaria todo lo que paso en la pelea?
	public void loggear(String mensaje) {
		Respuesta respuesta = new Respuesta();
		respuesta.agregarMensaje(mensaje);
	}

	public void enviar(String canal, String metodo, Map data) {
		Respuesta respuesta = new Respuesta();
		respuesta.put("canal", canal);
		respuesta.put("metodo", metodo);
		if (data != null)
			respuesta.put("data", data);
		
		handler.enviar(this.sessionId, respuesta);			
	}
	
	public void victoria() {
		niveles.pisarConAliados(nivelElegido, personajes);
		Respuesta respuesta = new Respuesta();
		respuesta.put("canal", "combate");
		respuesta.put("metodo", "victoria");
		handler.enviar(this.sessionId, respuesta);	

		combate.interrupt();
		combate = null;
		
	}
	
	public void derrota() {
		Respuesta respuesta = new Respuesta();
		respuesta.put("canal", "combate");
		respuesta.put("metodo", "derrota");
		handler.enviar(this.sessionId, respuesta);	

		combate.interrupt();
		combate = null;
		
	}
	
	
	public void animacionCompletada() {
		combate.setEstado(EstadoDeCombate.EN_ESPERA);
	}
	
	public void destruir() {}
		
	public void desconectar() {
		this.conectado = false;
		if (combate != null) combate.pausar();
	}
	
	public void conectar() {
		this.conectado = true;
		if (combate != null) combate.seguir();
	}
	
	public boolean conectado() {
		return conectado;
	}
	
	public void setHandler(CombateWebSocketHandler handler) {
		this.handler = handler;
	}
	
	

}