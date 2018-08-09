package farguito.sarlanga.seed.websocket;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.util.HtmlUtils;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.acciones.FabricaDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.CombateController;
import farguito.sarlanga.seed.combate.EstadoDeCombate;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.combate.controlador.PersonajeDeCombateDTO;
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.niveles.RepositorioDeNiveles;
import farguito.sarlanga.seed.websocket.objetos.TurnoAccionIN;
import farguito.sarlanga.seed.websocket.objetos.TurnoAccionOUT;

@Controller
@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS) //genero uno por conexion
public class CombateWebsocketController implements CombateController, ApplicationListener<SessionConnectEvent> {

	private String sessionId;
	private static final String COMBATE = 			"/combate";
	private static final String MENSAJES = 			COMBATE+"/mensajes";		
	private static final String ESTADO_PERSONAJES = COMBATE+"/personajes";
	
	@Autowired
    private SimpMessageSendingOperations template;
	

	@Autowired
	private FabricaDeCriaturas fabCriaturas;
	
	@Autowired
	private FabricaDeAcciones fabAcciones;
	
	@Autowired
	private RepositorioDeNiveles niveles;
	
	private SistemaDeCombate combate;

	private List<Aliado> personajes;
	private Integer nivelElegido;
	
	
	@PostConstruct
	private void init() {
		System.out.println("init");
		combate = new SistemaDeCombate();
		combate.setControlador(this);
	}
	
    @MessageMapping(COMBATE+"/accion") //lo que viene de JS
    public void turnoAccion(TurnoAccionIN turnoAccion){ 
    	try{
    		combate.accionar(turnoAccion.getObjetivoId(), combate.getPersonajeActivo().getAcciones().get(turnoAccion.getAccionId()));    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @MessageMapping(COMBATE+"/pasar-nivel")
	public void pasarNivel() {
		try {
			if(combate.getEstado() == EstadoDeCombate.VICTORIA)
				niveles.pisarConAliados(nivelElegido, personajes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
    
    @MessageMapping(COMBATE+"/iniciar")
	public void iniciar(
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
		loggear(respuesta);
	}
    
    private void loggear(Respuesta r) {
    	((List<String>) r.get("mensajes")).stream().forEach(m ->{
    		loggear(m);
    	});
    }
    
	public void loggear(String mensaje) {
		enviar(MENSAJES, new TurnoAccionOUT(HtmlUtils.htmlEscape(mensaje)));
	}

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		this.sessionId = SimpMessageHeaderAccessor.getSessionId(headers);		
	}
	
	private void enviar(String canal, Object objeto) { 				
		if(sessionId != null) {
			template.convertAndSend(canal+"-"+sessionId, objeto);
		}
	}
	
	

}