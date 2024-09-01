package launcher;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LecturaFichero {

    public static String contenidoFichero(String fichero, Charset codificacion){
        String s = null;

        if (existeFichero(fichero)){
            try {
                byte [] contenido = Files.readAllBytes(Paths.get(fichero));
                s = new String(contenido, codificacion);
                
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error de E/S");
            }
        }
        return s;
    }

    public static boolean existeFichero(String fichero){
        File archivoEntrada = new File(fichero);
        return archivoEntrada.exists();
    }
}