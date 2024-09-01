package nebrija.traductor;
import java.nio.charset.Charset;

import launcher.LecturaFichero;


public class Lexico {

    private PalabrasReservadas componentesLexicos;
    private int posicion;
    private int lineas;
    private char caracter;
    private String programa;

    public Lexico (String programa) {

        this.posicion=0;
        this.lineas=1;
        this.componentesLexicos = new PalabrasReservadas("lexico.txt");
        this.programa = programa + (char) (0);
    }

    public Lexico(String ficheroEntrada, Charset codificacion) {

        this(LecturaFichero.contenidoFichero(ficheroEntrada, codificacion)); //estoy llamando al otro constructor

        this.posicion=0;
        this.lineas=1;
        this.componentesLexicos = new PalabrasReservadas("lexico.txt");
        
    }

    private char extraeCaracter() {
        return this.programa.charAt(this.posicion++);
    }

    private boolean extraeCaracter(char c) {
        if (this.posicion < this.programa.length() -1) {
            this.caracter=extraeCaracter();

            if(c==this.caracter)
                return true;
            else {
                devuelveCaracter();

                return false;
            }
        }
        else
            return false;
    }

    private void devuelveCaracter() {
        this.posicion--;
    }




    public int getLineas() {
        return this.lineas;
    }

    public ComponenteLexico getComponenteLexico() { // analizador para descartar espacios tabuladores etc
        String etiquetaLexica;
        while (true) {
            this.caracter=extraeCaracter();

            if (this.caracter == 0)
                return new ComponenteLexico("end_program");
            else if (this.caracter == ' ' || (int) this.caracter == 9 || (int) this.caracter == 13) //char = 9 (tabulacion), char = 13 (retorno de carro)
                continue;
            else if((int) this.caracter==10) //char = 10 indica linea nueva
                this.lineas++;
            else
                break;
        }

        if (Character.isLetter(this.caracter) ) {
            String lexema = "";

            do {
                lexema = lexema + this.caracter;
                this.caracter = extraeCaracter();
            }while(Character.isLetterOrDigit(this.caracter));

            devuelveCaracter();

            etiquetaLexica = componentesLexicos.getEtiqueta(lexema);

            if(etiquetaLexica == null)
                return new Identificador(lexema);
            else
                return new ComponenteLexico(etiquetaLexica);
        }

        if (Character.isDigit(this.caracter)) {
            String numero = "";
            do {
                numero = numero + this.caracter;

                this.caracter = extraeCaracter();
            } while (Character.isDigit(this.caracter));

            if (this.caracter != '.') {
                devuelveCaracter();

                return new NumeroEntero(Integer.parseInt(numero));
            }

            do {
                numero = numero + this.caracter;

                this.caracter = extraeCaracter();
            } while (Character.isDigit(this.caracter));

            devuelveCaracter();

            return new NumeroReal(Float.parseFloat(numero));
        }

        String lexema = "", lexemaAlternativo, etiquetaAlternativa;

        do {
            lexema = lexema + this.caracter;
            etiquetaLexica = componentesLexicos.getEtiqueta(lexema);

            if(etiquetaLexica == null)
                return new ComponenteLexico("invalid_char");
            else {
                lexemaAlternativo = lexema;
                this.caracter = extraeCaracter();

                lexemaAlternativo = lexemaAlternativo + this.caracter;
                etiquetaAlternativa = componentesLexicos.getEtiqueta(lexemaAlternativo);

                if(etiquetaAlternativa != null)
                    etiquetaLexica = etiquetaAlternativa;

            }
        }while(etiquetaAlternativa != null);

        devuelveCaracter();

        return new ComponenteLexico(etiquetaLexica);
    }
}
