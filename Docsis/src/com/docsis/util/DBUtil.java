package com.docsis.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.docsis.exception.DaoException;





public class DBUtil {

	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	//localhost
	private static final String URL_DATABASE = "jdbc:mysql://186.237.60.141/docsis"; //:3306;databaseName=docsis;";
	private static final String DRIVER_JDBC = "com.mysql.jdbc.Driver";
	
	private static final String USUARIO_DB = "tvanet";
	private static final String SENHA_USUARIO_DB = "vaiof233";
	
	static {
		try {
			LOG.info("URL DATABASE : " + URL_DATABASE);
			LOG.info("DRIVER JDBC : " + DRIVER_JDBC);
			LOG.info("Carregando driver ");
			Class.forName(DRIVER_JDBC);
			LOG.info("Driver carregado");
			
//			String path = "C:/Users/katia magalhaes/Desktop/projetos_tcc/TccPizzaria_JDesktop/lib/native/win32-x86/";
//			System.loadLibrary(path + "civil.dll");
//			System.loadLibrary(path + "swt-awt-win32-3139.dll");
//			System.loadLibrary(path + "swt-awt-win32-3235.dll");
//			System.loadLibrary(path + "swt-gdip-win32-3139.dll");
//			System.loadLibrary(path + "swt-gdip-win32-3235.dll");
//			System.loadLibrary(path + "swt-wgl-win32-3235.dll");
//			System.loadLibrary(path + "swt-win32-3139.dll");
//			System.loadLibrary(path + "swt-win32-3235.dll");
			
			
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws DaoException {
		try {
			Connection connection = DriverManager.getConnection(URL_DATABASE, USUARIO_DB, SENHA_USUARIO_DB);
			connection.setAutoCommit(true);
			LOG.debug("Conexao " + connection + " obtida");
			return connection;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	public static java.util.Date getJavaDate(ResultSet result, String nomeCampo) throws SQLException {
		Date dataFalecimento = result.getDate(nomeCampo);
		java.util.Date javaDate = null;
		if (dataFalecimento != null) {
			javaDate = new Date(dataFalecimento.getTime());
		}
		return javaDate;
	}
	
	public static byte[] getImage(ResultSet result, String nomeCampo) throws DaoException {
		InputStream is = null;
		try {

			return result.getBytes(nomeCampo);

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new DaoException(e);
				}
			}
		}

	}
	
	public static Date getSqlDate(java.util.Date date) {
		Date sqlDate = null;
		if (date != null) {
			sqlDate = new java.sql.Date(date.getTime());
		}
		return sqlDate;
	}
	
	public static void close(Connection conn, Statement statement, ResultSet result) {
		try {
			if (conn != null) {
				LOG.debug("Fechando conexao " + conn);
				conn.close();
				LOG.debug("Conexao " + conn + " fechada");
			}
			if (statement != null) {
				LOG.debug("Fechando statement " + statement);
				statement.close();
				LOG.debug("Statement " + statement + " fechado");
			}
			if (result != null) {
				LOG.debug("Fechando ResultSet " + result);
				result.close();
				LOG.debug("ResultSet " + result + " fechado");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getArrayImage(Image img) throws DaoException {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream(8000);
			
			BufferedImage bufferedImage = 
				new BufferedImage(
						img.getWidth(null), 
						img.getHeight(null), 
						BufferedImage.TYPE_INT_RGB);
			
			
			ImageIO.write(bufferedImage, "jpg", baos);
			
			baos.flush();
			
			return baos.toByteArray();
			
		} catch (IOException e) {
			throw new DaoException(e);
		} finally {
			close(baos);
		}
	}

	public static void close(Closeable closeable) throws DaoException {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw new DaoException(e);
			}
		}
	}
}
