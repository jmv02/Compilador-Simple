package launcher;

import java.nio.charset.StandardCharsets;

import AnalizadorSintactico.AnalizadorSintactico;
import nebrija.traductor.ComponenteLexico;
import nebrija.traductor.Lexico;

public class LauncherCompilador {
		public static void main(String[] args) {
			ComponenteLexico etiquetaLexica; 
			
			String fichero="Programa1.txt"; 
			Lexico lexico = new Lexico(fichero,StandardCharsets.UTF_8);
			int c = 0; 
			
			do {
				etiquetaLexica = lexico.getComponenteLexico(); 
				System.out.println("<" + etiquetaLexica.toString() + ">"); 
				c++; 
		}while(!etiquetaLexica.getEtiqueta().equals("end_program")); 
			System.out.println("\n Componentes Lexicos: " + c + ",Lineas: "+ lexico.getLineas()); 
			
			AnalizadorSintactico compilador = new AnalizadorSintactico(new Lexico(fichero,StandardCharsets.UTF_8));
			
			System.out.println("compilacion de sentencia de declaracion de variables\n"); 
			
			compilador.programa();

			System.out.println("Tabla de simbolos \n\n"+ compilador.tablaSimbolos());
			
		}
}
