package conexao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import util.LeitorDeConfiguracao;


/**
 * @author ronald.serrao
 * 
 */
public class ConexaoBD {

	private LeitorDeConfiguracao leitorCofiguracao = null;
	//
	private String jdbcDriver = null;
	private String dataBasePrefix = null;
	private String portaBancoDeDados = null;
	private String nomeBancoDeDados = null;
	private String maquina = null;
	private String usuario = null;
	private String senha = null;
	private String url = null;
	//	
	public static Connection con;
	//	
	static Logger logger = LogManager.getLogger(ConexaoBD.class.getName());

	/**
	 * 
	 * @param nomeArquivo   jdbc:postgresql://localhost:5432/testdb
	 */
	public ConexaoBD(String nomeArquivo) {

		super();

		logger.info("Entrei no ConexaoBD");
		
		this.leitorCofiguracao = new LeitorDeConfiguracao(nomeArquivo);
        // 
		this.jdbcDriver = leitorCofiguracao.getValue("jdbcDriver");
		this.dataBasePrefix = leitorCofiguracao.getValue("dataBasePrefix");
		this.portaBancoDeDados = leitorCofiguracao.getValue("portaBancoDeDados");
		this.nomeBancoDeDados = leitorCofiguracao.getValue("nomeBancoDeDados");
		this.maquina = leitorCofiguracao.getValue("maquina");
		this.usuario = leitorCofiguracao.getValue("usuario");
		this.senha = leitorCofiguracao.getValue("senha");
		//
		url = dataBasePrefix + "" + maquina + ":" + portaBancoDeDados + "/" + nomeBancoDeDados;
		con = null;
		logger.info(url);
		
	}

	/**
	 * Cria a conexao com o banco de dados.
	 * 
	 * @return
	 */
	public Connection conecta() {
		
		
		logger.info("Entrando no conecta()");
		
		try {
			
			
			// Class.forName((jdbcDriver);
			
			//
			Class.forName(this.jdbcDriver);
			logger.info("Drive :"+ this.jdbcDriver);
			con = DriverManager.getConnection(url, usuario, senha);
			con.setAutoCommit(false);
			logger.info(String.valueOf("Conexão com sucesso!!")); 
			return con;
			
		} catch (Exception e) {
			e.printStackTrace();
			//
			logger.error(String.valueOf("Erro ao tentar se conectar ao banco!!"));
			return null;
		}
	}

	/**
	 * Fecha a conexao com o banco de dados.
	 * 
	 * @return
	 */
	public boolean desconecta() {
		if (con != null) {
			try {
				con.close();
				logger.debug(String.valueOf("Conseguiu fechar a conexão!"));
				return true;

			} catch (SQLException e) {
				
				logger.error(String.valueOf("Não conseguiu fechar a conexao!"));
				return false;
			}
		}
		return false;
	}

}