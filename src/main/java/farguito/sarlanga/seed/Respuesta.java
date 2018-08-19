package farguito.sarlanga.seed;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//refactorizame pls
public class Respuesta extends LinkedHashMap<String, Object> {

	
	public Respuesta(String key, Object value) {
		super();
		put(key, value);
	}


	public Respuesta() {}

	public void agregar(String key, Object value) {
		if(value != null) 
			put(key, value);
	}

	public void agregarNodo(String nodoPadre, String nodoHijo, Object value) {
		if(containsKey(nodoPadre)) {
			((Respuesta) this.get(nodoPadre)).put(nodoHijo, value);
		}
	}

	public void agregarNodo(String nodoPadre, Object value) {
		if(containsKey(nodoPadre)) {
			Respuesta padre = (Respuesta) this.get(nodoPadre);
			padre.put(""+padre.size(), value);
		}
	}

	public void agregarNodo(Object value) {
		put(""+this.size(), value);
	}
	
	
	public void error(Exception e) {
		put("error", e.getMessage());
	}
	
	public void agregar(Respuesta respuesta) {
		if(!respuesta.isEmpty())
			respuesta.entrySet().stream().forEach(set -> {
				if(set.getValue() != null)
					put(set.getKey(), set.getValue());
			});
	}

	public void agregarMensaje(String entrada) {
		if(entrada != null) {
			if(this.containsKey("mensajes")) {
				Map<String, String> mensajes = (Map) get("mensajes");
				mensajes.put(String.valueOf(mensajes.size()+1), entrada);
			} else {
				Map<String, String> mensajes = new LinkedHashMap();
				mensajes.put(String.valueOf(mensajes.size()+1), entrada);
				this.put("mensajes", mensajes);
			}
		}
	}
	

	public void agregarMensaje(List<String> entrada) {
		if(entrada != null) {
			if(this.containsKey("mensajes")) {
				Map<String, String> mensajes = (Map) get("mensajes");
				entrada.stream().forEach(m -> {
					mensajes.put(String.valueOf(mensajes.size()+1), m);
				});
			} else {
				Map<String, String> mensajes = new LinkedHashMap();
				entrada.stream().forEach(m -> {
					mensajes.put(String.valueOf(mensajes.size()+1), m);
				});
				this.put("mensajes", mensajes);
			}
		}
	}
	
	public void agregarMensaje(Respuesta entrada) {
		if(entrada != null && !entrada.isEmpty()) {
			Map<String, String> mensajesEntrantes = (Map) entrada.get("mensajes");
			if(this.containsKey("mensajes")) {
				Map<String, String> mensajes = (Map) get("mensajes");
				mensajesEntrantes.values().stream().forEach(m -> {
					mensajes.put(String.valueOf(mensajes.size()+1), m);
				});
			} else {
				Map<String, String> mensajes = new LinkedHashMap();
				mensajesEntrantes.values().stream().forEach(m -> {
					mensajes.put(String.valueOf(mensajes.size()+1), m);
				});
				this.put("mensajes", mensajes);
			}
		}
	}


	public void exito() {
		this.put("exito", true);	
	}
	
}
