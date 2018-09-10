package farguito.sarlanga.seed.acciones;

import java.util.HashMap;
import java.util.Map;

//tengo que armarme una estructura que de la cual hereden esto y Respuesta, ya que se comportan igual
public class Efectos {

	private static Map agregar(String efecto, Object valor) {
		Map nuevo = new HashMap<>();

		nuevo.put(efecto, valor);
		
		return nuevo;
	}
	
	
	public static Map daño(Integer daño) {
		return agregar("daño", daño);
	}

	public static Map curacion(Integer curacion) {
		return agregar("curacion", curacion);
	}
	
}
