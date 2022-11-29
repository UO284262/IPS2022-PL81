package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//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

import mvc.modelo.ColegiadoSolicitadoDTO;

public class FileUtil {
	public static void appendToFile(String file, String toAppend)
	{
		FileWriter fichero;
		try {
			

			fichero = new FileWriter("files/" + file, true);

			fichero.append(toAppend);

			fichero.close();

		} catch (Exception ex) {
			System.out.println("Mensaje de la excepción: " + ex.getMessage());
		}
	}
	
	public static List<ColegiadoSolicitadoDTO> ReadFile(String file, String delimiter)
	{
		String linea;
	    String[] datosColegiado= null;
	    List<ColegiadoSolicitadoDTO> ret = new ArrayList<ColegiadoSolicitadoDTO>();
	    
	    try {
	    	   BufferedReader fichero = new BufferedReader(new FileReader("files/" + file));
	    		while (fichero.ready()) {
	    			ColegiadoSolicitadoDTO dto = new ColegiadoSolicitadoDTO();
	    			linea = fichero.readLine();
	    			datosColegiado = linea.split(delimiter);
	    			dto.dni = datosColegiado[0];
	    			dto.nombre = datosColegiado[1];
	    			String[] titulacion = datosColegiado[2].split("-");
	    			for(String titulo : titulacion) dto.titulacion.add(titulo);
	    			ret.add(dto);
	    		}
	    		fichero.close();
	    }
	    catch (FileNotFoundException fnfe) {
	    }
	    catch (IOException ioe) {
	      new RuntimeException("Error de entrada/salida.");
	    }
	    return ret;
	}
	
	public static void deleteRecibidos() {
		File file = new File("files/recibirTitulacion.csv");
		file.delete();
	}
}
