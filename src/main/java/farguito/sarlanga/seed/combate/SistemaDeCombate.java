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

@RestController
@SessionScope
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SistemaDeCombate {
	
	PersonajeDeCombate personajeActivo;
	
	List<PersonajeDeCombate> personajes = new ArrayList<>();
	List<Aliado> aliados = new ArrayList<>();
	List<Enemigo> enemigos = new ArrayList<>();
	
	EstadoDeCombate estado = EstadoDeCombate.EN_ESPERA;
	
	@GetMapping("/iniciar")
	public void iniciar(
			List<Aliado> aliados,
			List<Enemigo> enemigos
			) {

		personajes.addAll(aliados);
		personajes.addAll(enemigos);
		personajes.sort((pj1, pj2) -> pj1.velocidad - pj2.velocidad);
		
		aliados.addAll(aliados);
		enemigos.addAll(enemigos);
		
		personajeActivo = personajes.get(0); 
		if(personajeActivo instanceof Aliado)
			estado = EstadoDeCombate.TURNO_JUGADOR;
		else
			estado = EstadoDeCombate.TURNO_ENEMIGO;
		
	}
	
	
	public void accionar(
			PersonajeDeCombate objetivo,
			Accion accion
			) {
		estado = EstadoDeCombate.ANIMACION;
		
		accion.ejecutar(personajeActivo, objetivo);		
		
		personajeActivo = null;
		estado = EstadoDeCombate.EN_ESPERA;
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
		int i = 0;
		while(personajeActivo == null && i < personajes.size()) {
			if(personajes.get(i).enfriamiento <= 0)
				personajeActivo = personajes.get(i);
			else {
				personajes.get(i).descansar();
				i++;
			}
		}
				
		if(personajeActivo instanceof Aliado)
			estado = EstadoDeCombate.TURNO_JUGADOR;
		else
			estado = EstadoDeCombate.TURNO_ENEMIGO;
	}
	
	private void turnoEnemigo() {
		Enemigo enemigo = (Enemigo) personajeActivo;
		enemigo.decidir(personajes);
		accionar(enemigo.getEstrategia().getObjetivo(), enemigo.getEstrategia().getAccion());
	}
	
	
}
