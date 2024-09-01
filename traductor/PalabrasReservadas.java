package nebrija.traductor;



import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import launcher.LecturaFichero;
public class PalabrasReservadas {
	/*
	 * ComponentesLexicos es una tabla Hash (String, String) que
	 * almacena los componentes léxicos del lenguaje, definidos
	 * por parejas <lexema, etiquetaLexica> donde el lexema es
	 * la clave de la tabla y la etiqueta léxica el valor
	 */
	private Hashtable<String, String> componentesLexicos;
	public  PalabrasReservadas(String ficheroComponentesLexicos){
        this.componentesLexicos = new Hashtable<>();
        leeComponentesLexicos(ficheroComponentesLexicos);
    }

    public String getEtiqueta(String lexema) {
        return this.componentesLexicos.get(lexema);
    }

    public String getLexema(String etiquetaLexica) {
        String lexema = null;

        Set<Map.Entry<String, String>> s = this.componentesLexicos.entrySet();

        for(Map.Entry<String, String> m : s)
            if (m.getValue().equals(etiquetaLexica)) {
                lexema = m.getKey();
                break;
            }

        return lexema;
    }


    private void leeComponentesLexicos(String ruta){

        boolean existe = LecturaFichero.existeFichero(ruta);

        if (existe){
            try {
                Scanner fichero = new Scanner(new File(ruta), "UTF-8");
                String componenteLexico, lexema, etiquetaLexica;

                while (fichero.hasNext()) {
                    componenteLexico = fichero.nextLine();

                    if (componenteLexico.length() > 0 && componenteLexico.charAt(0) != '/') {


                        String[] componentes = componenteLexico.split(" ");

                        etiquetaLexica = componentes[0];
                        lexema = componentes[1];

                        componentesLexicos.put(lexema, etiquetaLexica);



                    }
                }

                fichero.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error de E/S");
            }

        }

    }
}
