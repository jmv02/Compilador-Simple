package nebrija.traductor;

public class TipoArray extends TipoDato{
private int tamano; 

public TipoArray(String tipo, int tamano) {
	super(tipo); 
	this.tamano = tamano; 
}
public int GetTamano() {
	return this.tamano; 
}
public String toString() {
	return super.toString() + " , " + this.tamano;
	}
}

