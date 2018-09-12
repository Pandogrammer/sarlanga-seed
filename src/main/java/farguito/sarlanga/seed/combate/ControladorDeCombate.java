package farguito.sarlanga.seed.combate;

import java.util.Map;

public interface ControladorDeCombate {

	public void loggear(String mensaje);

	public void victoria();
	
	public void derrota();

	public void enviar(String canal, String metodo, Map resultado);

	
}
