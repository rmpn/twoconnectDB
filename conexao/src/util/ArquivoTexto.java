package util;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class ArquivoTexto {

	public ArquivoTexto() {
		
	}
	
	
	public static Logger logger = LogManager.getLogger(ArquivoTexto.class.getName());
	
	public static void gravaLog(String mensagem) {
		
		
		String caminho="";
		File dir = new File (".");  
		logger.info(String.valueOf("Registrando o log ..."));
		try {
			
			caminho= dir.getCanonicalPath();
			
			FileWriter writer = new FileWriter(new File(caminho+"\\saida.txt"));
			PrintWriter saida = new PrintWriter(writer, true);
			saida.println(mensagem);
			saida.close();  
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
