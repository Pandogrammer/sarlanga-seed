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

import farguito.sarlanga.seed.websocket.objetos.CombateRequest;
import farguito.sarlanga.seed.websocket.objetos.IniciarDTO;

@Component
public class CombateWebSocketHandler extends TextWebSocketHandler {
	
	ObjectMapper mapper = new ObjectMapper();
	
	Map<String, CombateWebsocketController> sessions = new ConcurrentHashMap<>();

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		/* broadcast
		for(WebSocketSession webSocketSession : sessions) {
			Map value = new Gson().fromJson(message.getPayload(), Map.class);
			System.out.println(value.get("name"));
			webSocketSession.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
		}
		*/
		String sessionId = session.getId();
		CombateRequest request = mapper.readValue(message.getPayload(), CombateRequest.class);
		switch (request.getMetodo()) {
		case "iniciar" : IniciarDTO dto = mapper.convertValue(request.getData(), IniciarDTO.class);
						 sessions.get(sessionId).iniciar(dto.getPjs(), dto.getNivel()); 
						 break;
		default : break;
		}
	}
	
	
	//ponele que entendi por qué funciona con synchronized
	public synchronized void sendMessage(WebSocketSession session, String message) {
		try {
			session.sendMessage(new TextMessage(message));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("logueado: "+session.getId());		
		sessions.put(session.getId(), new CombateWebsocketController(session));
	}
	

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("deslogueado: "+session.getId());
		sessions.get(session.getId()).destruir();
		sessions.remove(session.getId());	
	}
	
}