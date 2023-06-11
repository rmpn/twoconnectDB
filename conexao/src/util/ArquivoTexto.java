package util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class ArquivoTexto {

	public ArquivoTexto() {
		
	}
	
	
	public static Logger logger = LogManager.getLogger(ArquivoTexto.class.getName());
	
	public static void gravaArquivo(String mensagem) {
		
		
		String caminho="";
		File dir = new File (".");  
		logger.info(String.valueOf("Registrando o log ..."));
		try {
			
			caminho= dir.getCanonicalPath();
			FileWriter writer = new FileWriter(new File(caminho+"\\"+"migrar_2Grau.txt"),true);
			//
			BufferedWriter bw  = new BufferedWriter(writer);
			bw.write(mensagem);
			bw.newLine();
			//
			bw.close();  
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		
			
		}
	}
	
public static void lerArquivo(String arquivo) {
		
		
		String caminho="";
		File dir = new File (".");  
		logger.info(String.valueOf("Registrando o log ..."));
		try {
			//
			caminho= dir.getCanonicalPath();
			//
			FileReader fr = new FileReader(caminho+"\\"+arquivo);
			BufferedReader br = new BufferedReader(fr);
			//
			while(br.ready() ){
			  //lê a proxima linha
			 String linha = br.readLine();
			  //faz algo com a linha
				
			}
			br.close();
			fr.close();
			//
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		
			
		}
	}
	

}
