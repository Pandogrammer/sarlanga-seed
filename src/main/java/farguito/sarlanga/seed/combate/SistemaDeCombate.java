package farguito.sarlanga.seed.combate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import farguito.sarlanga.seed.Jugador;
import farguito.sarlanga.seed.acciones.Accion;

public class SistemaDeCombate extends Thread {
	
	PersonajeDeCombate personajeActivo;
	
	List<PersonajeDeCombate> personajes = new ArrayList<>();
	
	//TODO: hacerlo mejor, no necesito TODO el personaje, solo id y enfriamiento, o armarme un map<id, pj> para targetear
	List<PersonajeDeCombate> turnos = new ArrayList<>(); 
	
	EstadoDeCombate estado = EstadoDeCombate.EN_ESPERA;
	
	
	public SistemaDeCombate(List<Aliado> aliados,List<Enemigo> enemigos) {
		
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
	}
	
	public String accionar(Integer objetivoId, Accion accion) {
		estado = EstadoDeCombate.ANIMACION;
		
		String mensaje = accion.ejecutar(personajeActivo, personajes.get(objetivoId));
		System.out.println(mensaje);
		
		personajeActivo = null;
		estado = EstadoDeCombate.EN_ESPERA;
		
		return mensaje;
	}

	boolean prendido = true;
	int segundosPausa = 100; //10
	public void seguir() {
		System.out.println("seguir");
		prendido = true;
	}
	public void pausar() {
		System.out.println("pausar");
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
		case EN_ESPERA: 
			avanzarTurnos(); break;
		case TURNO_ENEMIGO:
			turnoEnemigo(); break;
		default: break;
		}
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
						System.out.println("TURNO_JUGADOR");
					} else {
						estado = EstadoDeCombate.TURNO_ENEMIGO;
						System.out.println("TURNO_ENEMIGO");
					}
					
				} else {
					p.descansar();
				}
			}
			
			i++;
		}
		
	}
	
	private void turnoEnemigo() {
		System.out.println("turno enemigo");
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
	
	
	
	
	
}
