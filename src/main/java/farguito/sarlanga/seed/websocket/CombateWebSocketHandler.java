package farguito.sarlanga.seed.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import farguito.sarlanga.seed.Respuesta;
import farguito.sarlanga.seed.websocket.objetos.CombateRequest;
import farguito.sarlanga.seed.websocket.objetos.IniciarDTO;

@Component
public class CombateWebSocketHandler extends TextWebSocketHandler {
	
	ObjectMapper mapper = new ObjectMapper();

	private Map<String, WebSocketSession> sesiones = new ConcurrentHashMap<>();
	private Map<String, CombateWebSocketController> controladores = new ConcurrentHashMap<>();
	private Map<String, String> sesiones_controladores = new ConcurrentHashMap<>(); //LOL
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		try { 
			String sessionId = session.getId();
			CombateRequest request = mapper.readValue(message.getPayload(), CombateRequest.class);
			Respuesta respuesta = new Respuesta();
	
			if (request.getId() != null) respuesta.agregar("id", request.getId());
			switch (request.getMetodo()) {

			case "conectar" : {
				CombateWebSocketController controlador;
				if (!request.getData().isEmpty()) { //ya tiene sesion, se esta reconectando
					String oldSessionId = (String) request.getData().get("session_id");
					controlador = controladores.get(oldSessionId);
					controlador.setSessionId(sessionId);
					
					controladores.remove(oldSessionId);

				} else {					
					controlador = new CombateWebSocketController(sessionId);
					controlador.setHandler(this);
				}

				controladores.put(sessionId, controlador);

				
				Respuesta sessionIdRta = new Respuesta("session_id", sessionId); //puaj							
				respuesta.agregar("data", sessionIdRta);
				break;
			}

			case "reconectado" : {
				controladores.get(sessionId).conectar();
				break;
			}
			
			
			case "iniciar" : {
				IniciarDTO data = mapper.convertValue(request.getData(), IniciarDTO.class);
				respuesta.agregar("data", controladores.get(sessionId).iniciar(data.getPjs(), data.getNivel()));
				break;
			}
			
			case "informacion_nivel" : {
				respuesta.agregar("data", controladores.get(sessionId).informacionNivel(
													(Integer) request.getData().get("nivel"))); 
				break;
			}
			
			case "creacion_nivel" : {
				respuesta.agregar("data", controladores.get(sessionId).creacionNivel());
				break;
			}
			
			case "animacion_completada" : {
				controladores.get(sessionId).animacionCompletada();
				break;
			}
			
			case "accion_jugador" : {
				controladores.get(sessionId).accionJugador(
						(Integer) request.getData().get("objetivo"), 
						(Integer) request.getData().get("accion"));
				break;
			}
			
			default : break;
			
			}
			
			//si tiene id implica que espera una respuesta, sino se debe disparar desde el controller, pasarlo para alla directamente?
			if(request.getId() != null) 
				enviar(sessionId, respuesta);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(message.getPayload());
		}
	}

	public void broadcast(Map message) {
		for(String sessionId : sesiones.keySet()) {
			enviar(sessionId, message);
		}
	}
	
	
	//ponele que entendi por qué funciona con synchronized
	public synchronized void enviar(String sessionId, Map message) {
		
		try {
			System.out.println(mapper.writeValueAsString(message));
			if(controladores.get(sessionId).conectado())
				sesiones.get(sessionId).sendMessage(new TextMessage(mapper.writeValueAsString(message)));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	//TODO: mandarme el session id? y mandarlo en el request? usar seguridad onda JWT?
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String sessionId = session.getId();
		
		sesiones.put(sessionId, session);
		
		System.out.println("logueado: "+sessionId);
	}
	
	

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String sessionId = session.getId();
		
		controladores.get(sessionId).desconectar();

		sesiones.remove(sessionId);
		
		System.out.println("deslogueado: "+sessionId);
	}
	
}