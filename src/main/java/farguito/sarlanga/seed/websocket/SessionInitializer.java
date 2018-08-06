package farguito.sarlanga.seed.websocket;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class SessionInitializer extends AbstractHttpSessionApplicationInitializer { 

	public SessionInitializer() {
		super(SessionConfig.class); 
	}
}