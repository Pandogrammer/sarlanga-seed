package farguito.sarlanga.seed.combate.controlador;

import java.io.Serializable;

import farguito.sarlanga.seed.combate.PersonajeDeCombate;

public class PersonajeDTO implements Serializable {
	
	private Integer id;
	private String nombre;
	private String vida;
	private boolean vivo;
	
	public PersonajeDTO(PersonajeDeCombate p) {
		this.id = p.getId();
		this.nombre = p.getNombre();
		this.vida = p.getVida()+"/"+p.getVidaMax();
		this.vivo = p.isVivo();		
	}
	
	public Integer getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getVida() {
		return vida;
	}
	public boolean isVivo() {
		return vivo;
	}
	
	

}
