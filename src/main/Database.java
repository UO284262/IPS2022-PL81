package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import application.util.exceptions.ApplicationException;
import application.util.exceptions.UnexpectedException;

/**
 * Encapsula los datos de acceso JDBC, lectura de la configuracion
 * y scripts de base de datos para creacion y carga.
 */
public class Database  {

	private static final String APP_PROPERTIES = "src/main/resources/application.properties";
	private static final String SQL_SCHEMA = "src/main/resources/schema.sql";
	private static final String SQL_LOAD = "src/main/resources/data.sql";
	private static boolean databaseCreated=false;
	
	private String driver;
	private String url;
	private String user;
	private String pass;
	

	
	public Database() {
		
		// Combino ambas clases en una pero se puede hacer por separado
		// Abajo pongo los métodos que tenía el DbUtil
		
		// Leo el fichero
		
		Properties prop=new Properties();			
		try {
			prop.load(new FileInputStream(APP_PROPERTIES));
		} catch (IOException e) {
			//throw new ApplicationException(e);
		}

		// Leo los parámetros y hago las comprobaciones
		
		driver=prop.getProperty("datasource.driver");
		url=prop.getProperty("datasource.url");
		user=prop.getProperty("datasource.user");
		pass=prop.getProperty("datasource.pass");
		

		if (url==null || user==null || pass ==null || driver == null)
			//throw new ApplicationException("Problemas a la hora de configurar");
		
		try {
			Class.forName( driver );
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se ha encontrado el driver", e);
		}
		

		if (url==null || user==null || pass ==null || driver == null);
			//throw new ApplicationException("Problemas a la hora de configurar");
	}
	

	/**
	 * Para devolver la conexión
	 * @return Un objeto Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, pass);
	}

	
	
	// PARA REALIZAR OPERACIONES CON LA BASE DE DATOS
	
	
	/** 
	 * Creacion de una base de datos limpia a partir del script schema.sql en src/main/properties
	 * (si onlyOnce=true solo ejecutara el script la primera vez
	 */
	public void createDatabase(boolean onlyOnce) {
		//actua como singleton si onlyOnce=true: solo la primera vez que se instancia para mejorar rendimiento en pruebas
		if (!databaseCreated || !onlyOnce) { 
			executeScript(SQL_SCHEMA);
			databaseCreated=true; //NOSONAR
		}
	}
	/** 
	 * Carga de datos iniciales a partir del script data.sql en src/main/properties
	 * (si onlyOnce=true solo ejecutara el script la primera vez
	 */
	public void loadDatabase() {
		executeScript(SQL_LOAD);
	}
	
	/**
	 * Metodo simple para ejecutar todas las sentencias sql que se encuentran en un archivo, teniendo en cuenta:
	 * <br/>- Cada sentencia DEBE finalizar en ; pudiendo ocupar varias lineas
	 * <br/>- Se permiten comentarios de linea (--)
	 * <br/>- Todas las sentencias drop se ejecutan al principio, 
	 * y se ignoran los fallos en caso de que no exista la tabla (solo para drop)
	 */
	public void executeScript(String fileName) {
		List<String> lines = null;
		try {
			lines=Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			//throw new ApplicationException(e);
		}
		//separa las sentencias sql en dos listas, una para drop y otra para el resto pues se ejecutaran de forma diferente
		List<String> batchUpdate=new ArrayList<>();
		List<String> batchDrop=new ArrayList<>();
		StringBuilder previousLines=new StringBuilder(); //guarda lineas anteriores al separador (;)
		for (String line : lines) {
			line=line.trim();
			if (line.length()==0 || line.startsWith("--")) //ignora lineas vacias comentarios de linea
				continue;
			if (line.endsWith(";")) {
				String sql=previousLines.toString()+line;
				//separa drop del resto
				if (line.toLowerCase().startsWith("drop"))
					batchDrop.add(sql);
				else
					batchUpdate.add(sql);
				//nueva linea
				previousLines=new StringBuilder();
			} else {
				previousLines.append(line+" ");
			}
		}
		//Ejecuta todas las sentencias, primero los drop (si existen)
		if (!batchDrop.isEmpty())
			this.executeBatchNoFail(batchDrop);
		if (!batchUpdate.isEmpty())
			this.executeBatch(batchUpdate);
	}
	
	
	/**
	 * Ejecuta un conjunto de sentencias sql de actualizacion en un unico batch
	 */
	public void executeBatch(List<String> sqls) {
		try (Connection cn= getConnection();
			Statement stmt = cn.createStatement()) {
				for (String sql : sqls)
					stmt.addBatch(sql);
				stmt.executeBatch();
		} catch (SQLException e) {
			//Ojo, no dejar pasar las excepciones (no limitarse a dejar el codigo autoegenerado por Eclipse con printStackTrace)
			//throw new UnexpectedException(e);
		}
	}
	/**
	 * Ejecuta un conjunto de sentencias sql de actualizacion en un unico batch, sin causar excepcion cuando falla la ejecucion
	 * (usado normalmente para borrar tablas de la bd, que fallarian si no existen)
	 */
	public void executeBatchNoFail(List<String> sqls) {
		try (Connection cn=getConnection();
			Statement stmt = cn.createStatement()) {
				for (String sql : sqls)
					executeWithoutException(stmt,sql);
		} catch (SQLException e) {
			//throw new UnexpectedException(e);
		}
	}
	private void executeWithoutException(Statement stmt, String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			//no causa excepcion intencionaamente
		}		
	}
	
	// EXTRA
	
	/**
	 * Ejecuta una query sql con los parametros especificados mapeando el resultet en una lista de arrays de objetos;
	 * Utiliza apache commons-dbutils para relizar el mapeo y el manejo del resto de aspectos de jdbc
	 */
	public List<Object[]> executeQueryArray(String sql, List<String> querrys) {
		Connection conn=null;
		List<Object[]> returns = new ArrayList<Object[]>();
		Statement st = conn.createStatement();
		try {
			conn=this.getConnection();
			for(String querry : querrys)
			{
				st.executeQuery(querry).;
			}
			//Como no hay una clase especificada para realizar el mapeo, utiliza el ArrayListHandler
			return runner.query(conn, sql, beanListHandler, params);
		} catch (SQLException e) {
			throw new UnexpectedException(e);
		} finally {
			conn.close();
		}
	}
	

}
