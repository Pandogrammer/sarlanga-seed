package farguito.sarlanga.seed.websocket;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.util.HtmlUtils;

import farguito.sarlanga.seed.acciones.Accion;
import farguito.sarlanga.seed.acciones.Acciones;
import farguito.sarlanga.seed.acciones.FabricaDeAcciones;
import farguito.sarlanga.seed.combate.Aliado;
import farguito.sarlanga.seed.combate.CombateController;
import farguito.sarlanga.seed.combate.SistemaDeCombate;
import farguito.sarlanga.seed.criaturas.Criaturas;
import farguito.sarlanga.seed.criaturas.FabricaDeCriaturas;
import farguito.sarlanga.seed.criaturas.Personaje;
import farguito.sarlanga.seed.niveles.RepositorioDeNiveles;
import farguito.sarlanga.seed.websocket.objetos.TurnoAccionIN;
import farguito.sarlanga.seed.websocket.objetos.TurnoAccionOUT;

@Controller
@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS) //genero uno por conexion
public class CombateWebsocketController implements CombateController, ApplicationListener<SessionConnectEvent> {

	@Autowired
    private SimpMessageSendingOperations template;
	
	//TODO: hacerlo bien. y lindo. no esta chanchada.
	String header = "{simpMessageType=MESSAGE, contentType=application/json;charset=UTF-8}";
	String header_2 = "{simpMessageType=MESSAGE, conversionHint=method 'turnoAccion' parameter -1, contentType=application/json;charset=UTF-8, simpSessionId=afizlwxo}";
	

	private String id;

	@Autowired
	private FabricaDeCriaturas fabCriaturas;
	
	@Autowired
	private FabricaDeAcciones fabAcciones;
	
	@Autowired
	private RepositorioDeNiveles niveles;
	
	private SistemaDeCombate combate;
	
	List<String> mensajes = new ArrayList<>();
	
	@PostConstruct
	private void iniciar() {
		List<Aliado> personajes = new ArrayList<>();
		
		Personaje pjPersonaje = this.fabCriaturas.crear(Criaturas.GOLEM);
		
		List<Accion> pjAcciones = new ArrayList<>();	
		Accion ac = fabAcciones.crear(Acciones.GOLPE);
		pjAcciones.add(ac);

		personajes.add(new Aliado(pjPersonaje, pjAcciones));
		
		combate = new SistemaDeCombate();
		combate.setControlador(this);
		combate.iniciar(personajes, niveles.get(1).getEnemigos());	
	}
	
///*
    @MessageMapping("/accion") //lo que viene de JS
    //@SendToUser("/combate/mensajes") //a donde lo mando
    public void turnoAccion(TurnoAccionIN turnoAccion) throws Exception { 
    	combate.accionar(turnoAccion.getObjetivoId(), combate.getPersonajeActivo().getAcciones().get(turnoAccion.getAccionId()));    	
    }
//*/
	
/*
    @MessageMapping("/accion") //lo que viene de JS
    @SendToUser("/combate/mensajes") //a donde lo mando
    public TurnoAccionOUT turnoAccion(TurnoAccionIN turnoAccion) throws Exception { 
    	String mensaje = "@SendToUser ";
        mensaje += combate.accionar(turnoAccion.getObjetivoId(), combate.getPersonajeActivo().getAcciones().get(turnoAccion.getAccionId()));
    	return new TurnoAccionOUT(HtmlUtils.htmlEscape(mensaje));
    }
//*/
	public void loggear(String mensaje) { 		
		String canal = "/combate/mensajes";
		Map<String, Object> headers = new HashMap<>();
		headers.put("simpSessionId", id);
		
		if(id != null) {
			mensaje = id+": "+mensaje;
			template.convertAndSendToUser(id, canal, new TurnoAccionOUT(HtmlUtils.htmlEscape(mensaje)), headers);
			template.convertAndSend(canal, new TurnoAccionOUT(HtmlUtils.htmlEscape(mensaje)), headers);
		}
	}


	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		this.id = SimpMessageHeaderAccessor.getSessionId(headers);		
	}
	
	

}