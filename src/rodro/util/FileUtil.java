package rodro.util;

import java.io.*;
import java.util.*;

import rodro.modelo.ReciboDto;


public abstract class FileUtil {


	public static void saveToFile(String nombreFicheroSalida, List<ReciboDto> listaRecibos) { 
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter("files/" + nombreFicheroSalida + ".dat"));
			String linea = listaRecibos.toString();
			fichero.write(linea);
			fichero.close();
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha podido guardar");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida");
		}
	}
}
