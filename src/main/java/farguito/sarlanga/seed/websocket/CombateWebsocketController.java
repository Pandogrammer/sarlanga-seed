package farguito.sarlanga.seed.websocket;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CombateWebsocketController implements CombateController {
	
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
	
	
    @MessageMapping("/accion") //lo que viene de JS
    @SendTo("/combate/mensajes") //a donde lo mando
    public TurnoAccionOUT turnoAccion(TurnoAccionIN turnoAccion) throws Exception { 
    	String mensaje = combate.accionar(turnoAccion.getObjetivoId(), combate.getPersonajeActivo().getAcciones().get(turnoAccion.getAccionId()));
    	
        return new TurnoAccionOUT(HtmlUtils.htmlEscape(mensaje));
    }
    
	public void loggear(String mensaje) {    	
		turnoEnemigo(mensaje);
	}
	
	@SubscribeMapping("/combate/mensajes")
	public TurnoAccionOUT turnoEnemigo(String mensaje) {
        return new TurnoAccionOUT(HtmlUtils.htmlEscape(mensaje));
	}

}