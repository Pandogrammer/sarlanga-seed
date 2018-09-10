package farguito.sarlanga.seed.criaturas;

public enum Criaturas {
	
	 RATA  ("Rata")
   , GOLEM ("Golem")
   , QUIMERA ("Quimera")
   ;
   private String nombre;
	
   private Criaturas(String n) {
	   nombre = n;
   }
   
   public String toString() {
	   return this.nombre;
   }

}
