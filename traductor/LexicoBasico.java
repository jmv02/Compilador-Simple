/*

package nebrija.traductor;

import java.util.Hashtable;
public class LexicoBasico {
 // palabrasReservadas: tabla Hash de palabras reservadas
 // posicion: posicion del caracter actual
 // lineas: numero de lineas del programa
 // caracter: caracter actual devuelto por extraeCaracter()
 // programa: codigo fuente del programa
 private Hashtable<String, String> palabrasReservadas;
 private Hashtable<String,String> componentesLexicos;
 private int posicion;
 private int lineas;
 private char caracter;
 private String programa;
 public LexicoBasico(String programa) {
 this.posicion = 0;
 this.lineas = 1;
 // la tabla Hash de palabras reservadas almacena el lexema
 // (clave) y el token (valor), la etiqueta del token coincide
 // con el lexema de la palabra reservada
 this.palabrasReservadas = new Hashtable<String, String>();
 this.palabrasReservadas.put("break", "break");
 this.palabrasReservadas.put("do", "do");
 this.palabrasReservadas.put("else", "else");
 this.palabrasReservadas.put("float", "float");
 this.palabrasReservadas.put("for", "for");
 this.palabrasReservadas.put("if", "if");
 this.palabrasReservadas.put("int", "int");
 this.palabrasReservadas.put("while", "while");
 // al final del programa se a�ade el car�cter 0 para indicar
 // el final, cuando el analizador l�xico encuentra este car�cter
 // devuelve el token �end_program�
 this.programa = programa + (char) (0);
 }
 private char extraeCaracter() {
	 return this.programa.charAt(this.posicion++);
	 }
	 private void devuelveCaracter() {
	 this.posicion--;
	 }
	 // extraeCaracter(char c) se usa para reconocer operadores con
	 // lexemas de dos caracteres: &&, ||, <=, >=, ==, !=
	 private boolean extraeCaracter(char c) {
	 if (this.posicion < this.programa.length() - 1) {
	 this.caracter = extraeCaracter();
	 if (c == this.caracter)
	 return true;
	 else {
	 devuelveCaracter();
	 return false;
	 }
	 }
	 else
	 return false;
	 }
	 public int getLineas() {
	 return this.lineas;
	 }
	 // la clase Character de Java ofrece los m�todos:
	 // - Character.isDigit(char c): devuelve true si c es un d�gito
	 // - Character.isLetter(char c): devuelve true si c es una letra
	 // - Character.isLetterOrDigir(char c): devuelve true si c es una letra o un digito
	 // estos m�todos se usan para reconocer identificadores y n�meros.
	 // aplicando las expresiones regulares:
	 // - id = letra (letra | digito )*
	 // - numero = digito+ ( . digito+ )?
	 
	 public ComponenteLexicoBasico getComponenteLexico() {
		 // el analizador l�xico descarta los espacios (c�digo 32),
		 // tabuladores (c�digo 9) y saltos de l�nea (c�digos 10 y 13)
		 while (true) {
		 this.caracter = extraeCaracter();
		 if (this.caracter == 0)
		 return new ComponenteLexicoBasico("end_program");
		 else if (this.caracter == ' ' || (int) this.caracter == 9 ||
		 (int) this.caracter == 13)
		 continue;
		 else if((int) this.caracter == 10)
		 this.lineas++;
		 else
		 break;
		 }
		 // secuencias de d�gitos de n�meros enteros o reales
		 if (Character.isDigit(this.caracter)) {
		 String numero = "";
		 do {
		 numero = numero + this.caracter;
		 this.caracter = extraeCaracter();
		 } while (Character.isDigit(this.caracter));
		 if (this.caracter != '.') {
		 devuelveCaracter();
		 return new ComponenteLexicoBasico("int", numero);
		 }

		 do {
		 numero = numero + this.caracter;
		 this.caracter = extraeCaracter();
		 } while (Character.isDigit(this.caracter));
		 devuelveCaracter();
		 return new ComponenteLexicoBasico("float", numero);
		 }
		// identificadores y palabras reservadas
		 if (Character.isLetter(this.caracter) ) {
		 String lexema = "";
		 do {
		 lexema = lexema + this.caracter;
		 this.caracter = extraeCaracter();
		 } while(Character.isLetterOrDigit(this.caracter));

		 devuelveCaracter();
		 if (this.palabrasReservadas.containsKey(lexema))
		 return new
		 ComponenteLexicoBasico((String)
		 this.palabrasReservadas.get(lexema));
		 else
		 return new ComponenteLexicoBasico("id", lexema);
		 }

		 // operadores aritmeticos, relacionales, logicos y
		 // caracteres delimitadores
		 switch (this.caracter) {
		// Operadores relacionales y de asignacion, aritmeticos y logicos
		 case '=': return new ComponenteLexicoBasico("assignment");
		 //case '==': return new ComponenteLexicoBasico("equals");
		//case '!=': return new ComponenteLexicoBasico("");
		 case '<': return new ComponenteLexicoBasico("less_than");
		//case '<=': return new ComponenteLexicoBasico("less_equals");
		 case '>': return new ComponenteLexicoBasico("greater_than");
		//case '>=': return new ComponenteLexicoBasico("greater_equals");
		//case '&&': return new ComponenteLexicoBasico("and");
		//case '||': return new ComponenteLexicoBasico("or");
		 case '!': return new ComponenteLexicoBasico("not");
		 case '+': return new ComponenteLexicoBasico("add");
		 case '-': return new ComponenteLexicoBasico("subtract");
		 case '*': return new ComponenteLexicoBasico("multiply");
		 case '/': return new ComponenteLexicoBasico("divide");
		 case '%': return new ComponenteLexicoBasico("remainder");
		 //Delimitadores
		 case '.': return new ComponenteLexicoBasico("dot");
		 case ',': return new ComponenteLexicoBasico("coma");
		 case ';': return new ComponenteLexicoBasico("semicolon");
		 case '(': return new ComponenteLexicoBasico("open_parenthesis");
		 case ')':return new ComponenteLexicoBasico("closed_parenthesis");
		 case '{': return new ComponenteLexicoBasico("open_bracket");
		 case '}': return new ComponenteLexicoBasico("closed_bracket");
		 case '[': return new ComponenteLexicoBasico("open_square_bracket");
		 case ']': return new ComponenteLexicoBasico("closed_square_bracket");
		 case '#': return new ComponenteLexicoBasico("end_of_program");
		 default: return new ComponenteLexicoBasico("invalid_char");
		 }

	}
}


*/