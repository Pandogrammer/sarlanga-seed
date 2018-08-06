package farguito.sarlanga.seed.websocket;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import farguito.sarlanga.seed.combate.SistemaDeCombate;

@Controller
public class GreetingController {

	@Autowired
	SistemaDeCombate sistema;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {        
    	List<String> mensajes = sistema.getMensajes(); //asi no explota
    	String mensaje = "";
    	if(!mensajes.isEmpty())
    		mensaje = mensajes.get(mensajes.size()-1);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(mensaje) + "!");
    }

}