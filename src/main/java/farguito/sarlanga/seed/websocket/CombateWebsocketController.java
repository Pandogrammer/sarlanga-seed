package farguito.sarlanga.seed.websocket;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.SarlangaContext;
import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.acciones.FabricaDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.ControladorDeCombate;
import farguito.sarlanga.seed.combate.EstadoDeCombate;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.combate.controlador.PersonajeDeCombateDTO;
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.niveles.RepositorioDeNiveles;
import farguito.sarlanga.seed.websocket.objetos.TurnoAccionIN;

public class CombateWebSocketController implements ControladorDeCombate {
		
	private FabricaDeCriaturas fabCriaturas;
	
	private FabricaDeAcciones fabAcciones;
	
	private RepositorioDeNiveles niveles;
	
	private SistemaDeCombate combate;
	
	private String sessionId;
	private CombateWebSocketHandler handler;
	
	private List<Aliado> personajes;
	private Integer nivelElegido;
	
	//is this frula?
	public CombateWebSocketController(String sessionId) {
		this.sessionId = sessionId;
		fabCriaturas = (FabricaDeCriaturas) SarlangaContext.getAppContext().getBean("fabricaDeCriaturas");
		fabAcciones = (FabricaDeAcciones) SarlangaContext.getAppContext().getBean("fabricaDeAcciones");
		niveles = (RepositorioDeNiveles) SarlangaContext.getAppContext().getBean("repositorioDeNiveles");
		handler = (CombateWebSocketHandler) SarlangaContext.getAppContext().getBean("combateWebSocketHandler");
	}
	
    public void turnoAccion(TurnoAccionIN turnoAccion){ 
    	try{
    		combate.accionar(turnoAccion.getObjetivoId(), combate.getPersonajeActivo().getAcciones().get(turnoAccion.getAccionId()));    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

	public void pasarNivel() {
		try {
			if(combate.getEstado() == EstadoDeCombate.VICTORIA)
				niveles.pisarConAliados(nivelElegido, personajes);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				combate.setControlador(this);
				combate.iniciar(personajes, niveles.get(nivel).getEnemigos());		
	
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
    
	private void enviarOtro(String sessionId, Respuesta respuesta) {
	   handler.enviar(sessionId, respuesta);
	}
	
	private void enviarSesion(Respuesta respuesta) {		
		handler.enviar(this.sessionId, respuesta);		
	}
	
	private void enviarTodos(Respuesta respuesta) {
		handler.broadcast(respuesta);
	}
	
	public void loggear(String mensaje) {
		Respuesta respuesta = new Respuesta();
		respuesta.agregarMensaje(mensaje);
		handler.enviar(this.sessionId, respuesta);
	}
	

	public String getSessionId() {
		return sessionId;
	}

	public void destruir() {}
	
	

}