package farguito.sarlanga.seed.combate;

import java.util.Map;

public interface ControladorDeCombate {

	public void loggear(String mensaje);

	public void animarTurno(Map resultado);
	
	public void turnoJugador(Map resultado);

	public void estadoTurnos(Map resultado);

	
}
