package nebrija.traductor;

public class NumeroReal extends ComponenteLexico {
	 private float valor;
	 public NumeroReal(float valor) {
		 super("float");
		 this.valor= valor;
	 }
	 public float getValor() {
	 return valor;
	 }
	 public String toString() {
	 return super.toString() + ", " + this.getValor();
	 }
	}