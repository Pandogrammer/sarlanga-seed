package farguito.sarlanga.seed.combate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import farguito.sarlanga.seed.acciones.Accion;

public class SistemaDeCombate extends Thread {
	
	private PersonajeDeCombate personajeActivo;
	
	private List<PersonajeDeCombate> personajes = new ArrayList<>();
	
	//TODO: hacerlo mejor, no necesito TODO el personaje, solo id y enfriamiento, o armarme un map<id, pj> para targetear
	private List<PersonajeDeCombate> turnos = new ArrayList<>(); 
	
	private EstadoDeCombate estado = EstadoDeCombate.EN_ESPERA;
	
	//falso logger
	private List<String> mensajes = new ArrayList<>();
	//DUDAS
	private ControladorDeCombate controlador;

	public boolean iniciar(List<Aliado> aliados,List<Enemigo> enemigos) {
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
		
		personajeActivo = turnos.get(0); 
		if(personajeActivo instanceof Aliado)
			estado = EstadoDeCombate.TURNO_JUGADOR;
		else
			estado = EstadoDeCombate.TURNO_ENEMIGO;
		
		
		this.start();
		
		return true;
	}
	
	public String accionar(Integer objetivoId, Accion accion) {
		estado = EstadoDeCombate.ANIMACION;
		
		String mensaje = accion.ejecutar(personajeActivo, personajes.get(objetivoId));
		log(mensaje);
		
		personajeActivo = null;
		estado = EstadoDeCombate.EN_ESPERA;
		
		return mensaje;
	}

	boolean prendido = true;
	int segundosPausa = 100; //10
	public void seguir() {
		log("seguir");
		prendido = true;
	}
	public void pausar() {
		log("pausar");
		prendido = false;
	}
	
	//websocket
	public void run() {
		try {
			while(true) {
				if(prendido) {
					actualizar();
					Thread.sleep(segundosPausa);
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
	
	private void victoria() {
		this.interrupt();
	}
	
	private void derrota() {
		this.interrupt();
	}
	
	private void avanzarTurnos() {
		turnos.sort((pj1, pj2) -> pj2.getEnfriamiento() - pj1.getEnfriamiento());
		int i = 0;
		PersonajeDeCombate p = null;
		while(personajeActivo == null && i < turnos.size()) {
			p = turnos.get(i);
			
			if(p.isVivo()) {
				if(p.getEnfriamiento() <= 0) {
					personajeActivo = p;
					
					if(personajeActivo instanceof Aliado) {
						estado = EstadoDeCombate.TURNO_JUGADOR;
						log("TURNO_JUGADOR");
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
		
		
	}
	
	private void turnoEnemigo() {
		Enemigo enemigo = (Enemigo) personajeActivo;
		enemigo.getEstrategia().preparar(personajes);
		accionar(enemigo.getEstrategia().getObjetivo().getId(), enemigo.getEstrategia().getAccion());
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
	
	public boolean isTurnoJugador() {
		return estado.equals(EstadoDeCombate.TURNO_JUGADOR);
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

	public List<String> getMensajes() {
		return mensajes;
	}
	
	private void log(String mensaje) {
		mensajes.add(mensaje);
		controlador.loggear(mensaje);
		//System.out.println(mensaje);		
	}

	//sacarlo despues?
	public void setControlador(ControladorDeCombate controlador) {
		this.controlador = controlador;
	}

	
	
	
	
}
