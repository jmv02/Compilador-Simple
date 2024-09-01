package nebrija.traductor;

public class TipoDato {
private String tipo ; 

public TipoDato(String tipo) {
	this.tipo = tipo; 
}

public String getTipo() {
	return this.tipo;
	
}
public String toString() {
	return tipo.toString() ; 
}
}
