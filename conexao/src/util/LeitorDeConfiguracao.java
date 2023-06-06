package util;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Properties;
//
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LeitorDeConfiguracao {
	
	public static Logger logger = LogManager.getLogger(LeitorDeConfiguracao.class.getName());

	static Properties properties = null;

	public LeitorDeConfiguracao(String confFile) {
		
		logger.info(String.valueOf("Iniciando a leitura.."));
		properties = new Properties();
		try {
			//FileInputStream f = new FileInputStream("con_oracle.properties");
			InputStream f = getClass().getClassLoader().getResourceAsStream(confFile);
			properties.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public LeitorDeConfiguracao(Reader confFile) {
		properties = new Properties();
		try {			
			properties.load(confFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LeitorDeConfiguracao() {
		
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		String res = null;
		res = properties.getProperty(key);
		return res;
	}
	/**
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public String getValue(String path, String key) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String res = properties.getProperty(key);
		return res;
	}

	public boolean setValue(String path, String key,String value) {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("##"+(String)properties.setProperty(key, value));

		try {
			properties.store(new FileOutputStream(path),"");
		} catch (IOException e) {
			e.printStackTrace();
		}


		return true;
	}
	/**
	 * 
	 * @param file
	 * @return
	 */
	public LinkedList<String> getContents(File file) {

		BufferedReader bufferedReader = null;
		String linha = "";
		LinkedList list = new LinkedList();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file.getAbsoluteFile())));

			linha = bufferedReader.readLine();
			while (linha != null) {
				list.add(linha);
				linha = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return list;
	}


	/**
	 *
	 * @param file
	 * @return
	 */
	public byte[] retrieveArrayByte(File file) {
		byte code[] = null;
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			code = new byte[(int) file.length()];
			fis.read(code);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return code;
	}

}
