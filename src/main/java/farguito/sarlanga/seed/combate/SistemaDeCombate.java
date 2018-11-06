package farguito.sarlanga.seed.combate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import farguito.sarlanga.seed.acciones.Accion;

public class SistemaDeCombate extends Thread {
	
	private PersonajeDeCombate personajeActivo;
	
	private List<PersonajeDeCombate> personajes = new ArrayList<>();
	
	//TODO: hacerlo mejor, no necesito TODO el personaje, solo id y enfriamiento, o armarme un map<id, pj> para targetear
	private List<PersonajeDeCombate> turnos = new ArrayList<>(); 
	
	private EstadoDeCombate estado = EstadoDeCombate.ANIMACION;
	
	//falso logger
	private List<String> mensajes = new ArrayList<>();
	//DUDAS
	private ControladorDeCombate controlador;


	private boolean prendido = true;
	private int segundosPausa = 100; //10
	
	public void run() {
		try {
			while(true) {
				if(prendido) {
					actualizar();
					//Thread.sleep(segundosPausa); //para websocket no hace falta.
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizar() {
		switch(estado) {
		case EN_ESPERA:     avanzarTurnos(); chequearVidas(); break;
		case TURNO_ENEMIGO: turnoEnemigo(); break;
		case VICTORIA:      victoria(); break;
		case DERROTA:       derrota(); break;		
		default: break;
		}
	}
	
	
	public boolean preparar(List<Aliado> aliados,List<Enemigo> enemigos) {
		personajes = new ArrayList<>();
		turnos = new ArrayList<>();
		
		aliados.stream().forEach(p -> {
			p.setId(personajes.size());
			personajes.add(p);
			turnos.add(p);
		});
		
		enemigos.stream().forEach(p -> {
			p.setId(personajes.size());
			personajes.add(p);
			turnos.add(p);
		});		
		
		turnos.sort((pj1, pj2) -> pj2.getVelocidad() - pj1.getVelocidad());
		
		return true;
	}
	
	public Map accionar(Integer objetivoId, Accion accion) {
		estado = EstadoDeCombate.ANIMACION;
		
		Map resultado = accion.ejecutar(personajeActivo, personajes.get(objetivoId));

		resultado.put("objetivo_aliado", personajes.get(objetivoId) instanceof Aliado);		
		
		log((String) resultado.get("mensaje"));
		
		personajeActivo.postTurno();		
		personajeActivo = null;
		
		return resultado;
	}

	
	private void avanzarTurnos() {
		turnos.sort((pj1, pj2) -> pj2.getCansancio() - pj1.getCansancio());
		int i = 0;
		PersonajeDeCombate p = null;
		while(personajeActivo == null && i < turnos.size()) {
			p = turnos.get(i);
			
			if(p.isVivo()) {
				if(p.getCansancio() <= 0) {
					personajeActivo = p;
					p.preTurno();
					
					
					if(personajeActivo instanceof Aliado) {
						estado = EstadoDeCombate.TURNO_JUGADOR;
						log("TURNO_JUGADOR");
						avisarTurnoJugador();
					} else {
						estado = EstadoDeCombate.TURNO_ENEMIGO;
						log("TURNO_ENEMIGO");
					}
					
				} else {
					p.descansar();
				}
			}
			
			i++;
		}

		estadoTurnos();
	}
	
	private void avisarTurnoJugador() {
		Map resultado = new HashMap<>();		
		resultado.put("id", personajeActivo.getId());		
		controlador.enviar("combate", "turno_jugador", resultado);
		
	}
	
	
	private void turnoEnemigo() {
		Enemigo enemigo = (Enemigo) personajeActivo;
		enemigo.getEstrategia().preparar(personajes);
		Integer objetivoId = enemigo.getEstrategia().getObjetivo().getId();
		Accion accion = enemigo.getEstrategia().getAccion();
		
		Map resultado = accionar(objetivoId, accion);

		controlador.enviar("combate", "animar_turno", resultado);
	}
	

	private void chequearVidas() {
		boolean aliadoVivo = false;
		boolean enemigoVivo = false;
		aliadoVivo = (personajes.stream().anyMatch(pj -> pj.isVivo() && pj instanceof Aliado));
		enemigoVivo = (personajes.stream().anyMatch(pj -> pj.isVivo() && pj instanceof Enemigo));

		if(enemigoVivo && !aliadoVivo) {
			estado = EstadoDeCombate.DERROTA;
			log("DERROTA");
		}
		else if(aliadoVivo && !enemigoVivo) {
			estado = EstadoDeCombate.VICTORIA;
			log("VICTORIA");
		}
	}
	

	
	public Map estadoTurnos() {
		Map resultado = new HashMap<>();
		List<Map> estados = new ArrayList<>();		
		
		personajes.stream().forEach(pj -> {
			Map estado = new HashMap<>();
			estado.put("vida", pj.getVida());
			estado.put("vida_max", pj.getVidaMax());
			estado.put("vivo", pj.isVivo());
			estados.add(estado);
		});
		
		resultado.put("estados", estados);

		controlador.enviar("combate", "estado_turnos", resultado);	
		
		return resultado;		
	}
	


	public void seguir() {
		log("seguir");
		prendido = true;
	}
	public void pausar() {
		log("pausar");
		prendido = false;
	}
	
	public void iniciar() {
		this.start();		
	}
	

	private void victoria() {
		this.prendido = false;
		controlador.victoria();
	}
	
	private void derrota() {
		this.prendido = false;
		controlador.derrota();
	}
	
	
	
	
	
	public List<PersonajeDeCombate> getPersonajes() {
		return personajes;
	}

	public PersonajeDeCombate getPersonajeActivo() {
		return personajeActivo;
	}

	public EstadoDeCombate getEstado() {
		return estado;
	}
		
	public void setEstado(EstadoDeCombate estado) {
		this.estado = estado;
	}

	public boolean isTurnoJugador() {
		return estado.equals(EstadoDeCombate.TURNO_JUGADOR);
	}
	
	public List<String> getMensajes() {
		return mensajes;
	}
	
	private void log(String mensaje) {
		mensajes.add(mensaje);
		controlador.loggear(mensaje);	
	}

	//sacarlo despues?
	public void setControlador(ControladorDeCombate controlador) {
		this.controlador = controlador;
	}
	
}
