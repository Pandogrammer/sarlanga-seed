package farguito.sarlanga.seed.acciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioDeAcciones {
	
	Map<Integer, Accion> acciones = new HashMap<>();
	
	public RepositorioDeAcciones() {}
	
	public void save(Accion accion) {
		this.acciones.put(acciones.size(), accion);
	}
	
	public Accion find(Integer id) {
		return this.acciones.get(id);
	}

	public List<Accion> findById(List<Integer> ids){
		List<Accion> acciones = new ArrayList<>();
		ids.stream().forEach(id -> {
			acciones.add(this.acciones.get(id));
		});
			
		return acciones;
	}
}
