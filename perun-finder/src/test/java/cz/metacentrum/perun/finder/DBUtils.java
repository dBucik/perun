package cz.metacentrum.perun.finder;

import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

	/**
	 * Reads SQL statements from file. SQL commands in file must be separated by
	 * a semicolon.
	 *
	 * @param file file to be read
	 * @return array of command  strings
	 */
	private static String[] readSqlStatements(File file) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			return result.toString().split(";");
		} catch (IOException ex) {
			throw new RuntimeException("Cannot read " + file.getAbsolutePath(), ex);
		}
	}

	/**
	 * Executes SQL script.
	 *
	 * @param ds datasource where the script should execute
	 * @param scriptFile script file to be executed
	 * @throws SQLException when operation fails
	 */
	public static void executeSqlScript(DataSource ds, File scriptFile) throws SQLException {
		try (Connection conn = ds.getConnection()) {
			for (String sqlStatement : readSqlStatements(scriptFile)) {
				if (!sqlStatement.trim().isEmpty()) {
					conn.prepareStatement(sqlStatement).executeUpdate();
				}
			}
		}
	}

	public static void setUpDatabaseTables(DataSource ds, Resource tablesFile, Resource dataFile) throws Exception {
		DBUtils.executeSqlScript(ds, tablesFile.getFile());
		DBUtils.executeSqlScript(ds, dataFile.getFile());
	}
}
