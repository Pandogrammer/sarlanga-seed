package farguito.sarlanga.seed.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/combate");
        config.setApplicationDestinationPrefixes("/app");	
    }
    
    @Override
    public void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/mensajes").setAllowedOrigins("*").setHandshakeHandler(new DefaultHandshakeHandler() {
        	 
            public boolean beforeHandshake(
              ServerHttpRequest request, 
              ServerHttpResponse response, 
              WebSocketHandler wsHandler,
              Map attributes) throws Exception {
        
                  if (request instanceof ServletServerHttpRequest) {
                      ServletServerHttpRequest servletRequest
                       = (ServletServerHttpRequest) request;
                      HttpSession session = servletRequest
                        .getServletRequest().getSession();
                      attributes.put("sessionId", session.getId());
                  }
                      return true;
              }}).withSockJS();
        
        registry.addEndpoint("/mensajes").setAllowedOrigins("*").withSockJS();
    }
    


}