package pje;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessoDAO {
	

		
	
	
public Connection con;

	
	static Logger logger = LogManager.getLogger(ProcessoDAO.class.getName());

	public ProcessoDAO(Connection con) {
		
		this.con = con;
	
	}
	
	public List<Processo2G> getProcessoPJE2G(String docautor, String docreu) {
		
       List<Processo2G> processos = new ArrayList<Processo2G>();
	   //	
       Processo2G processo;
		
	   String p;
		
	   logger.info("######### ProcessoDAO ############ ");
	      
	   String sqlProcesso = "select "
		   		+ "    p.id_processo, "
		   		+ "    p.nr_processo, "
		   		+ "    ptrf.ds_proc_referencia as nr_processo_refe,   "
		   		+ "	ptrf.id_proc_referencia as id_proc_refe,  "
		   		+ "	oj.ds_orgao_julgador as orgao, "
		   		+ "	TO_CHAR(ptrf.dt_autuacao,'YYYY') as ano, "
		   		+ "	ptrf.dt_autuacao as autuacao, "
		   		+ "	cj.ds_classe_judicial as classe, "
		   		+ "	ul.ds_nome as poloa, "
		   		+ "	ul.ds_login as rfbpoloa, "
		   		+ "        case pp.in_participacao "
		   		+ "		when 'A' then 'Ativo' "
		   		+ "		when 'P' then 'Passivo' "
		   		+ "		when 'T' then 'outros' "
		   		+ "	end as poloatipo, "
		   		+ "	ux.ds_nome as polob, "
		   		+ "	ux.ds_login as rfbpolob, "
		   		+ "	case px.in_participacao "
		   		+ "		when 'A' then 'Ativo' "
		   		+ "		when 'P' then 'Passivo' "
		   		+ "		when 'T' then 'outros' "
		   		+ "	end as polobtipo "
		   		+ "from pje.tb_processo_trf as ptrf  "
		   		+ "inner JOIN pje.tb_processo as p on 	(ptrf.id_processo_trf = p.id_processo) "
		   		+ "INNER join pje.tb_orgao_julgador as oj on 	(ptrf.id_orgao_julgador = oj.id_orgao_julgador) "
		   		+ "INNER join pje.tb_processo_parte as pp on	(ptrf.id_processo_trf = pp.id_processo_trf) "
		   		+ "inner join pje.tb_tipo_parte as tp on	(pp.id_tipo_parte = tp.id_tipo_parte) "
		   		+ "inner join pje.tb_usuario_login as ul on	(pp.id_pessoa = ul.id_usuario) "
		   		+ "INNER join pje.tb_classe_judicial as cj on	(cj.id_classe_judicial = ptrf.id_classe_judicial) "
		   		+ "INNER join pje.tb_processo_parte as px  on (ptrf.id_processo_trf = px.id_processo_trf) "
		   		+ "inner join pje.tb_tipo_parte as tx on (tx.id_tipo_parte = px.id_tipo_parte) "
		   		+ "inner join pje.tb_usuario_login as ux on	(px.id_pessoa = ux.id_usuario) "
		   		+ "where 1 = 1  "
				+ "and p.nr_processo is not null  "
				//+ "and cj.cd_classe_judicial = '1265' "
				//+ "and REGEXP_REPLACE(ds_proc_referencia,'[^[:digit:]]','','g') = ? "
				+ "and ul.id_usuario in (  "
				+ "select	xx.id_pessoa  "
				+ "		from  "
				+ "		pje.tb_pess_doc_identificacao xx where REGEXP_REPLACE(xx.nr_documento_identificacao,'[^[:digit:]]','','g') = REGEXP_REPLACE( ?,'[^[:digit:]]','','g')  "
				+ " )	  "
				+ " and ux.id_usuario in (  "
				+ " select	yy.id_pessoa  "
				+ "		from  "
				+ "		pje.tb_pess_doc_identificacao yy where REGEXP_REPLACE(yy.nr_documento_identificacao,'[^[:digit:]]','','g') = REGEXP_REPLACE( ?,'[^[:digit:]]','','g')  "
				+ " )  ";
		//
		PreparedStatement stmtProcesso;
		try {
			stmtProcesso = con.prepareStatement(sqlProcesso);
			stmtProcesso.setString(1, docautor);
			stmtProcesso.setString(2, docreu);
			logger.info("##################### ");
			logger.info(" Processos comuns as partes: " + docautor + " e " + docreu);
			ResultSet rs = stmtProcesso.executeQuery();
			//
            while (rs.next()) {
            	//
            	logger.info("####### LOOP ########## ");
            	//
            	processo = new Processo2G(rs.getInt("id_processo"),
            			rs.getString("nr_processo"),
            			rs.getString("nr_processo_refe"),
            			rfbDoc(rs.getString("rfbpoloa")),
            			rs.getString("poloa"),
            			rfbDoc(rs.getString("rfbpolob")),
                        rs.getString("polob"));	
            	processos.add(processo);
            	//
            	logger.info(processo.toString());
            	//ArquivoTexto.gravaArquivo(String.valueOf(rp.toString()));
            	
            }
            
            return processos;
            
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return processos;
        
        }
	
public String rfbDoc (String valor) {
		
		String dummy = valor;
		
		
		if (valor.length() == 11) {
		
	    dummy = dummy.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	    
		} 
		
		if (valor.length() == 14) {
			
		    dummy = dummy.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3\\/$4-$5");
		    
		} 
		
		return dummy;
		
	}

public List<Processo1G> getProcessoPJE1G(String arquivo) {
	
	
	Integer idprocesso=0;
	String nrprocesso=""; 
	String nomeautor="";
	String docrfbautor="";
	String nomereu="";
	String docrfbreu="";
	int contador=0;
    List<Processo1G> processos = new ArrayList<Processo1G>();
	//	
    Processo1G processo;
    
    //
        
    String caminho="";
	File dir = new File (".");  
	logger.info(String.valueOf("Registrando o log ..."));
		
	String p;
		
	logger.info("######### ProcessoDAO ############ ");
	      
	String sqlProcesso = "select "
	   		+ "    lu.ds_login, "
	   		+ "    length(ds_login) as rfb, "
	   		+ "	pp.id_processo_parte, "
	   		+ "	pp.id_tipo_parte, "
	   		+ "	pp.id_pessoa, "
	   		+ "	o.ds_orgao_julgador, "
	   		+ "	p.id_processo, "
	   		+ "	p.nr_processo, "
	   		+ "	lu.ds_nome, "
	   		+ "	case "
	   		+ "		pp.in_participacao "
	   		+ "		when 'A' then 'Ativo' "
	   		+ "		when 'P' then 'Passivo' "
	   		+ "		when 'T' then 'Outros Participantes' "
	   		+ "	end as Polo, "
	   		+ "	pp.in_parte_principal, "
	   		+ "	case "
	   		+ "		pp.in_situacao "
	   		+ "		when 'A' then 'Ativo' "
	   		+ "		when 'I' then 'Inativo' "
	   		+ "	end as situacao,  "
	   		+ "	f.nm_agrupamento_fase as fase, "
	   		+ "	pp.nr_ordem "
	   		+ "from tb_processo_parte as pp "
	   		+ "inner join tb_usuario_login as lu on pp.id_pessoa = lu.id_usuario "
	   		+ "inner join tb_processo_trf as trf on trf.id_processo_trf = pp.id_processo_trf "
	   		+ "inner join tb_processo as p on p.id_processo = trf.id_processo_trf "
	   		+ "inner join tb_orgao_julgador as o on o.id_orgao_julgador = trf.id_orgao_julgador "
	   		+ "inner join pje_jt.tb_agrupamento_fase f on f.id_agrupamento_fase = p.id_agrupamento_fase "
	   		+ "where 1=1 "
	   		+ "and p.nr_processo = ? "
	   		+ "and pp.nr_ordem = 1 "
	   		+ "";
		//
		PreparedStatement stmtProcesso;
		try {
			
		stmtProcesso = con.prepareStatement(sqlProcesso);
		//-> ler arquivo aqui			
		//
		caminho= dir.getCanonicalPath();
				//
		FileReader fr = new FileReader(caminho+"\\"+arquivo);
		BufferedReader br = new BufferedReader(fr);
				//
		while(br.ready() ){
					
				String linha = br.readLine();
				//
				String[] textoSeparado = linha.split(";");
				stmtProcesso.setString(1, textoSeparado[2]);
				ResultSet rs = stmtProcesso.executeQuery();
				logger.info("######### Entrando no LooP ############ ");
				//
				contador = 0;
				//
				while (rs.next()) {
			         				         	
			         	//
					
					    contador ++;
			         	if (rs.getString("polo").equals("Ativo")) {
			       
			         		nomeautor = rs.getString("ds_nome");
			         		docrfbautor = rs.getString("ds_login");
			         		
			         	}else {
			         		
			         		nomereu = rs.getString("ds_nome");
			         		docrfbreu = rs.getString("ds_login");
			         		
			         	}
			         	
			         	if (contador == 1) {
			         		
			         	idprocesso = rs.getInt("id_processo");
			         	nrprocesso = rs.getString("nr_processo");
			         	
			         	}else{
			         	
			         		processo = new Processo1G(idprocesso,nrprocesso,nomeautor,docrfbautor,nomereu,docrfbreu);
			         		processos.add(processo);
			         	}
			         	
			         }  
								
				}
				br.close();
				fr.close();
				//
				
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			
				
			}
        
        return processos;
     }
	

public List<Processo1G> getProcessosPJE1G(String arquivo) {
	
	
	int idprocesso;
	String nrprocesso; 
	String nomeautor;
	String docrfbautor;
	String nomereu;
	String docrfbreu;
	
    List<Processo1G> processos = new ArrayList<Processo1G>();
	//	
    Processo1G processo;
    
    //
        
    String caminho="";
	File dir = new File (".");  
	logger.info(String.valueOf("Registrando o log ..."));
		
	String[] partes;
		
	logger.info("######### ProcessoDAO ############ ");
	      
	String sqlProcesso = "select "
			+ " p.id_processo, "
			+ "	p.nr_processo, "
			+ "    STRING_AGG (lu.ds_nome||';'||length(ds_login)||';'||lu.ds_login||';'||pp.in_participacao||';'||pp.nr_ordem||';'||pp.in_situacao,';' order by pp.in_participacao asc) as partes "
			+ "from pje.tb_processo_parte as pp "
			+ "inner join tb_usuario_login as lu on pp.id_pessoa = lu.id_usuario "
			+ "inner join tb_processo_trf as trf on trf.id_processo_trf = pp.id_processo_trf "
			+ "inner join tb_processo as p on p.id_processo = trf.id_processo_trf "
			+ "inner join tb_orgao_julgador as o on o.id_orgao_julgador = trf.id_orgao_julgador "
			+ "inner join pje_jt.tb_agrupamento_fase f on f.id_agrupamento_fase = p.id_agrupamento_fase "
			+ "where 1=1 "
			+ "and p.nr_processo = ? "
			+ "and pp.in_parte_principal = 'S' "
			+ "and pp.in_participacao not in ('T') "
			+ "and pp.nr_ordem = 1 "
			+ "group by p.nr_processo,p.id_processo";
	
	
		// '0017294-54.2018.5.16.0005'
	
		PreparedStatement stmtProcesso;
		try {
			
		stmtProcesso = con.prepareStatement(sqlProcesso);
		
		//-> ler arquivo aqui			
		
		caminho= dir.getCanonicalPath();
				//
		FileReader fr = new FileReader(caminho+"\\"+arquivo);
		BufferedReader br = new BufferedReader(fr);
				//
		while(br.ready() ){
					
				String linha = br.readLine();
				//
				String[] textoSeparado = linha.split(";");
				stmtProcesso.setString(1, textoSeparado[2]);
				ResultSet rs = stmtProcesso.executeQuery();
				// logger.info("######### Entrando no LooP ############ ");
				//
				while (rs.next()) {
			         				         	
			         	//
					    idprocesso = rs.getInt("id_processo");
	         	        nrprocesso = rs.getString("nr_processo");
	         	        String registro = rs.getString("partes").trim();
					    partes = registro.split(";");
					    // logger.info(nrprocesso +":" +partes.length);	
	         	        if (nrprocesso.equals("0132200-16.2011.5.16.0001")) {
	         	        	
	         	        	logger.info("0132200-16.2011.5.16.0001:"+partes.length);	
	         	    	   
	         	    	   for (int i = 0; i <= partes.length; i++) {
	         	    		   
	         	    	   System.out.println(partes[i]);
	         	    	   
	         	    	   }   
	         	       }
					    //
		         	    nomeautor = partes[0].trim();
		         	    docrfbautor = rfbDoc(partes[2].trim()); 
		         	    nomereu = partes[6].trim();
		         	    docrfbreu = rfbDoc(partes[8].trim());	   
			         	processo = new Processo1G(idprocesso,nrprocesso,nomeautor,docrfbautor,nomereu,docrfbreu);
			         	processos.add(processo);
			         	System.out.println(processo.toString());
			         	
			         	}
			         	rs.close();
			         }  
		
				br.close();
				fr.close();
				con.close();
				//
				
			} catch (IOException | SQLException | ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.getMessage();
				e.printStackTrace();
				
			}
        
        return processos;
     }
	
	}


