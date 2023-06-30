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
	
	public static ProcessoDAO nr;

	public static void main(String[] args) {
		
		//
		
		System.out.println("Iniciando ...");
		
		geBancoDeDados.conecta();
		      
		// logger.info(String.valueOf("Início ClienteDB: " + new SimpleDateFormat("dd/MM/yyyy 'as' hh:mm:ss").format(new Date())));
		
		/*
		rp = new RequisicaoPagamentoDAO(geBancoDeDados.conecta());
		List<?> reg = rp.getReqPag("01/01/1900","31/12/2023");
		System.out.println(" Linhas: "+reg.size());
		for(int i=0;i< reg.size();i++){
		   System.out.println(reg.get(i));
		} 
		
		*/
		nr = new ProcessoDAO(geBancoDeDados.conecta());
		nr.getProcessosNoPJE1G("processos_gprec_no_pje.txt");
		
		geBancoDeDados.desconecta();
		
		System.out.println("Fim ...");
			
			}
		
		
	
	}

