package app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import conexao.ConexaoBD;
import listas.RequisicaoPagamento;
import listas.RequisicaoPagamentoDAO;
import pje.ProcessoDAO;
import util.ArquivoLog;

public class ClienteBD {

	public static Logger logger = LogManager.getLogger(ClienteBD.class.getName());
	
	public static ConexaoBD geBancoDeDados = new ConexaoBD("con_pje.properties");
	
	public static RequisicaoPagamentoDAO rp;  

	public static void main(String[] args) {
			
		      
		logger.info(String.valueOf("Início ClienteDB: " + new SimpleDateFormat("dd/MM/yyyy 'as' hh:mm:ss").format(new Date())));
		/*		
		rp = new RequisicaoPagamentoDAO(geBancoDeDados.conecta());
		List<?> reg = rp.getReqPag("01/01/2000","31/12/2022");
		System.out.println(" Linhas: "+reg.size());
		for(int i=0;i< reg.size();i++){
		   System.out.println(reg.get(i));
		} 
		*/
		
		ProcessoDAO pd = new ProcessoDAO(geBancoDeDados.conecta());
		System.out.println(pd.toString());
		
		geBancoDeDados.desconecta();
			
			}
		
		
	
	}

