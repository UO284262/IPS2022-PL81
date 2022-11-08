package abel.modelo;

import java.io.FileWriter;

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
}
