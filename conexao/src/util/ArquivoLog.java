package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArquivoLog {
	
	
		public ArquivoLog() {
			
		}
		
       public ArquivoLog(String mensagem) {
			
		}	
       public ArquivoLog(String mensagem, String arquivo) {
			
		}
		
		public static Logger logger = LogManager.getLogger(ArquivoLog.class.getName());
		
		public static void gravaLog(String mensagem) {
			
			
			String caminho="";
			File dir = new File (".");  
			logger.info(String.valueOf("Registrando o log ..."));
			try {
				
				caminho= dir.getCanonicalPath();
				logger.info(String.valueOf(caminho));
				FileWriter writer = new FileWriter(new File(caminho+"\\"+"arquivo.txt"));
				//
				PrintWriter saida = new PrintWriter(writer, true);
				saida.println(mensagem);
				saida.close();  
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

public static void gravaLogFile(String mensagem,String arquivo) {
			
			
			String caminho="";
			File dir = new File (".");  
			logger.info(String.valueOf("Registrando o log ..."));
			try {
				
				caminho= dir.getCanonicalPath();
				logger.info(String.valueOf(caminho));
				FileWriter writer = new FileWriter(new File(caminho+"\\"+arquivo));
				//
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
