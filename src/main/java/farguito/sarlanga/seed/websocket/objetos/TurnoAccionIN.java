package farguito.sarlanga.seed.websocket.objetos;

public class TurnoAccionIN {
	
	private int accionId;
	
	private int objetivoId;
	

	public TurnoAccionIN() {}
	

	public int getAccionId() {
		return accionId;
	}

	public int getObjetivoId() {
		return objetivoId;
	}

	
	public TurnoAccionIN(int accionId, int objetivoId) {
		super();
		this.accionId = accionId;
		this.objetivoId = objetivoId;
	}
	
	

}
