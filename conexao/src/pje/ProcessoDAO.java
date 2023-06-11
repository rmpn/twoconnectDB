package pje;

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
	
	public List<Processo> getProcessoPJE(String docautor, String docreu) {
		
       List<Processo> processos = new ArrayList<Processo>();
	   //	
       Processo processo;
		
	   String p;
		
	   logger.info("######### ProcessoDAO ############ ");
	      
	   String sqlProcesso = "select\r\n"
		   		+ "    p.id_processo,\r\n"
		   		+ "    p.nr_processo,\r\n"
		   		+ "    ptrf.ds_proc_referencia as nr_processo_refe,  \r\n"
		   		+ "	ptrf.id_proc_referencia as id_proc_refe, \r\n"
		   		+ "	oj.ds_orgao_julgador as orgao,\r\n"
		   		+ "	TO_CHAR(ptrf.dt_autuacao,'YYYY') as ano,\r\n"
		   		+ "	ptrf.dt_autuacao as autuacao,\r\n"
		   		+ "	cj.ds_classe_judicial as classe,\r\n"
		   		+ "	ul.ds_nome as poloa,\r\n"
		   		+ "	ul.ds_login as rfbpoloa,\r\n"
		   		+ "        case pp.in_participacao\r\n"
		   		+ "		when 'A' then 'Ativo'\r\n"
		   		+ "		when 'P' then 'Passivo'\r\n"
		   		+ "		when 'T' then 'outros'\r\n"
		   		+ "	end as poloatipo,\r\n"
		   		+ "	ux.ds_nome as polob,\r\n"
		   		+ "	ux.ds_login as rfbpolob,\r\n"
		   		+ "	case px.in_participacao\r\n"
		   		+ "		when 'A' then 'Ativo'\r\n"
		   		+ "		when 'P' then 'Passivo'\r\n"
		   		+ "		when 'T' then 'outros'\r\n"
		   		+ "	end as polobtipo\r\n"
		   		+ "from pje.tb_processo_trf as ptrf \r\n"
		   		+ "inner JOIN pje.tb_processo as p on 	(ptrf.id_processo_trf = p.id_processo)\r\n"
		   		+ "INNER join pje.tb_orgao_julgador as oj on 	(ptrf.id_orgao_julgador = oj.id_orgao_julgador)\r\n"
		   		+ "INNER join pje.tb_processo_parte as pp on	(ptrf.id_processo_trf = pp.id_processo_trf)\r\n"
		   		+ "inner join pje.tb_tipo_parte as tp on	(pp.id_tipo_parte = tp.id_tipo_parte)\r\n"
		   		+ "inner join pje.tb_usuario_login as ul on	(pp.id_pessoa = ul.id_usuario)\r\n"
		   		+ "INNER join pje.tb_classe_judicial as cj on	(cj.id_classe_judicial = ptrf.id_classe_judicial)\r\n"
		   		+ "INNER join pje.tb_processo_parte as px  on (ptrf.id_processo_trf = px.id_processo_trf)\r\n"
		   		+ "inner join pje.tb_tipo_parte as tx on (tx.id_tipo_parte = px.id_tipo_parte)\r\n"
		   		+ "inner join pje.tb_usuario_login as ux on	(px.id_pessoa = ux.id_usuario)\r\n"
		   		+ "where 1 = 1 \r\n"
				+ "and p.nr_processo is not null \r\n"
				//+ "and cj.cd_classe_judicial = '1265' "
				//+ "and REGEXP_REPLACE(ds_proc_referencia,'[^[:digit:]]','','g') = ? "
				+ "and ul.id_usuario in ( \r\n"
				+ "select	xx.id_pessoa \r\n"
				+ "		from \r\n"
				+ "		pje.tb_pess_doc_identificacao xx where REGEXP_REPLACE(xx.nr_documento_identificacao,'[^[:digit:]]','','g') = REGEXP_REPLACE( ?,'[^[:digit:]]','','g') \r\n"
				+ " )	 \r\n"
				+ " and ux.id_usuario in ( \r\n"
				+ " select	yy.id_pessoa \r\n"
				+ "		from \r\n"
				+ "		pje.tb_pess_doc_identificacao yy where REGEXP_REPLACE(yy.nr_documento_identificacao,'[^[:digit:]]','','g') = REGEXP_REPLACE( ?,'[^[:digit:]]','','g') \r\n"
				+ " ) \r\n";
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
            	processo = new Processo(rs.getInt("id_processo"),
            			rs.getString("nr_processo"),
            			rs.getString("nr_processo_refe"),
            			rs.getString("poloa"),
            			rs.getString("rfbpoloa"),
                        rs.getString("polob"),
                        rs.getString("rfbpolob"));	
            	processos.add(processo);
            	//
            	logger.info(processo.getNrprocesso());
            	//ArquivoTexto.gravaArquivo(String.valueOf(rp.toString()));
            	
            }
            
            return processos;
            
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return processos;
        
        }
		
	}


