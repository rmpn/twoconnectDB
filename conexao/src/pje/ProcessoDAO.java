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
		
	   String sqlProcesso = "select "
				+ " p.id_processo, "
				+ " p.nr_processo,  "
				+ " ptrf.ds_proc_referencia as nr_processo_refe,  "
				+ " ptrf.id_proc_referencia,  "
				+ "	oj.ds_orgao_julgador as orgao, "
				+ "	TO_CHAR(ptrf.dt_autuacao,'YYYY') as ano, "
				+ "	ptrf.dt_autuacao as autuacao, "
				+ "	cj.ds_classe_judicial as classe, "
				+ "	ul.ds_nome as poloa, "
				+ "	ul.ds_login as rfbpoloa, "
				+ "	ux.ds_nome as polob, "
				+ "	ux.ds_login as rfbpolob, "
				+ "from pje.tb_processo_trf as ptrf  "
				+ "inner JOIN pje.tb_processo as p on 	(ptrf.id_processo_trf = p.id_processo) "
				+ "INNER join pje.tb_orgao_julgador as oj on 	(ptrf.id_orgao_julgador = oj.id_orgao_julgador) "
				+ "INNER join pje.tb_processo_parte as pp on	(ptrf.id_processo_trf = pp.id_processo_trf) "
				+ "inner join pje.tb_tipo_parte as tp on	(pp.id_tipo_parte = tp.id_tipo_parte) "
				+ "inner join pje.tb_usuario_login as ul on	(pp.id_pessoa = ul.id_usuario) "
				+ "INNER join pje.pje.tb_classe_judicial cj on	(cj.id_classe_judicial = ptrf.id_classe_judicial) "
				+ "INNER join pje.tb_agrupamento_fase fase on (fase.id_agrupamento_fase = p.id_agrupamento_fase) "
				+ "INNER join pje.tb_processo_parte as px on	(ptrf.id_processo_trf = px.id_processo_trf) "
				+ "inner join pje.tb_tipo_parte as tx on	(tx.id_tipo_parte = px.id_tipo_parte) "
				+ "inner join pje.tb_usuario_login as ux on	(px.id_pessoa = ux.id_usuario) "
				+ "where 1 = 1 "
				+ "and p.nr_processo is not null "
				//+ "and cj.cd_classe_judicial = '1265' "
				//+ "and REGEXP_REPLACE(ds_proc_referencia,'[^[:digit:]]','','g') = ? "
				+ "and ul.id_usuario in ( "
				+ "select	xx.id_pessoa "
				+ "		from "
				+ "		pje.tb_pess_doc_identificacao xx where REGEXP_REPLACE(xx.nr_documento_identificacao,'[^[:digit:]]','','g') = ? "
				+ " )	 "
				+ " and ux.id_usuario in ( "
				+ " select	yy.id_pessoa "
				+ "		from "
				+ "		pje.tb_pess_doc_identificacao yy where REGEXP_REPLACE(yy.nr_documento_identificacao,'[^[:digit:]]','','g') = ? "
				+ " ) ";
		//
		PreparedStatement stmtProcesso;
		docautor = "290.195.243-72";
		docreu = "06.309.123/0001-95";
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
            	processo = new Processo(rs.getInt("id_processo"),
            			rs.getString("nr_processo"),
            			rs.getString("nr_processo_refe"),
            			rs.getString("poloa"),
            			rs.getString("rfbpoloa"),
                        rs.getString("polob"),
                        rs.getString("rfbpolob"));	
            	processos.add(processo);
            	
            	System.out.println(rs.getString("nr_processo"));
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


