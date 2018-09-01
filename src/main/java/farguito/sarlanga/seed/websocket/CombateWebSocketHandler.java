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
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		try { 
			String sessionId = session.getId();
			CombateRequest request = mapper.readValue(message.getPayload(), CombateRequest.class);
			Respuesta rta = new Respuesta();
	
			if (request.getId() != null) rta.agregar("id", request.getId());
			switch (request.getMetodo()) {
			case "iniciar" : {
				IniciarDTO dto = mapper.convertValue(request.getData(), IniciarDTO.class);
				rta.agregar("data", controladores.get(sessionId).iniciar(dto.getPjs(), dto.getNivel()));
				break;
			}
			case "informacion_nivel" : {
				Map dto = mapper.convertValue(request.getData(), Map.class);
				rta.agregar("data", controladores.get(sessionId).informacionNivel((Integer) dto.get("nivel"))); 
				break;
			}
			case "creacion_nivel" : {
				Map dto = mapper.convertValue(request.getData(), Map.class);
				rta.agregar("data", controladores.get(sessionId).creacionNivel());
				break;
			}
			case "animacion_completada" : {
				controladores.get(sessionId).animacionCompletada();
				break;
			}
			default : break;
			}
			
			//si tiene id implica que espera una respuesta, sino se debe disparar desde el controller, pasarlo para alla directamente?
			if(request.getId() != null) enviar(sessionId, rta);
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
		//re-agarrar el controller si relogueo?
		CombateWebSocketController cc = new CombateWebSocketController(sessionId);
		cc.setHandler(this);
		
		if(controladores.containsKey(sessionId)) {
			controladores.get(sessionId).conectar();
		} else {		
			controladores.put(sessionId, cc);
		}
		
		sesiones.put(sessionId, session);
		
		System.out.println("logueado: "+sessionId);
	}
	

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String sessionId = session.getId();
		
		//mantener el controller vivo por si se vuelve a loguear?	
		//controladores.get(sessionId).desconectar();

		controladores.remove(sessionId);

		sesiones.remove(sessionId);
		
		System.out.println("deslogueado: "+sessionId);
	}
	
}