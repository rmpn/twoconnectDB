package gprec;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class RequisicaoPagamentoDAO {
	
	public Connection con;

	static Logger logger = LogManager.getLogger(RequisicaoPagamentoDAO.class.getName());

	public RequisicaoPagamentoDAO(Connection con) {
		this.con = con;
	}

	
	
	public List<RequisicaoPagamento> getRequisicaoPagamento(Integer anovencimento) {
        
		
		List<RequisicaoPagamento> requisicaoPagamento = new ArrayList<RequisicaoPagamento>();
		
		String queryProcesso = "SELECT r.IIDSOLICITACAO AS IIDSOLICITACAO, R.SNRREQUISICAO AS SNRREQUISICAO, k.SNRPROCESSO AS SNRPROCESSO, r.IANOVENCIMENTO as IANOVENCIMENTO FROM gep.TBREQUISICAOPAGAMENTO r "
				+ "inner JOIN GEP.TBSOLICITACAO S ON (S.IIDSOLICITACAO = r.IIDSOLICITACAO) "
				+ "INNER JOIN gep.tbprocesso k ON (s.iidprocesso = k.IIDPROCESSO) "
				+ "WHERE r.IANOVENCIMENTO = ?";
	
		PreparedStatement stmtProcesso;
		try {
			stmtProcesso = con.prepareStatement(queryProcesso);
			stmtProcesso.setInt(1, anovencimento);
			logger.debug("##################### ");
			logger.debug(" anovencimento: " + anovencimento);
			ResultSet rs = stmtProcesso.executeQuery();
			
            while (rs.next()) {
            	RequisicaoPagamento rp = new RequisicaoPagamento(
                        rs.getDouble("IIDSOLICITACAO"),
                        rs.getString("SNRREQUISICAO"),
                        rs.getString("SNRPROCESSO"),
                        rs.getInt("IANOVENCIMENTO"));
            	
            	requisicaoPagamento.add(rp);
            }
            
            return requisicaoPagamento;
            
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return requisicaoPagamento;
        
        }
    
public List<RequisicaoPagamento> getReqPag(String datini,  String datfim) {
        
		
		List<RequisicaoPagamento> requisicaoPagamento = new ArrayList<RequisicaoPagamento>();
		
		String queryProcesso = "SELECT  "
				+ "	s.iidsolicitacao, R.SNRREQUISICAO AS snrrequisicao,"
				+ "	substr(k.SNRPROCESSO, 1, 7)|| '-' || substr(k.SNRPROCESSO, 8, 2)|| '.' || substr(k.SNRPROCESSO, 10, 4)|| '.5.16.' || substr(k.SNRPROCESSO, 17, 4) AS snrprocesso,"
				+ "	s.sidtiposolicitacao AS tipo,"
				+ "	d.SIDESFERA AS ESFERA,"
				+ "	e.SNMSITUACAO AS SNMSITUACAO,"
				+ "	to_char(r.DDTAutuacao, 'dd/mm/yyyy') AS ddtautuacao,"
				+ "	decode(s.SFLFINALIZADA, 1, 'SIM', 'NAO') AS publicada,"
				+ "	decode(b.MVLLIQUIDO,0,nvl(b.MVLLIQUIDOORIGINAL,b.MVLLIQUIDO),b.MVLLIQUIDO)  AS valor,"
				+ "	s.SIDTIPOENTE AS ente,"
				+ "	substr(pj.SCNPJ, 1, 2)|| '.' || substr(pj.SCNPJ, 3, 3)|| '.' || substr(pj.SCNPJ, 6, 3)|| '/' || substr(pj.SCNPJ, 9, 4)|| '-' || substr(pj.SCNPJ, 13, 2) AS cnpjdevedor,"
				+ "	p.SNMPESSOA AS nomedevedor,"
				+ "	v.SNMVARA AS vara,"
				+ "	(r.IANOVENCIMENTO - to_number(to_char(sysdate, 'yyyy'))) AS prazo,"
				+ "	d.SIDESFERA AS esfera,"
				+ "	n.SNMPESSOA as nomecredor,"
				+ "	nvl(pfb.SCPF,pjb.SCNPJ) AS credorrfb,"
				+ "	r.IANOVENCIMENTO AS data_orc,"
				+ "	CASE WHEN e.iidsituacao NOT in (7) THEN "
				+ "		CASE "
				+ "			/* RPV Federal: ddtfimprazopagamento + 60 dias */ "
				+ "	    	WHEN s.sidtiposolicitacao = 'RPV' AND d.sidesfera = 'F' AND TRUNC(s.ddtfimprazopagamento + INTERVAL '60' DAY) < TRUNC(sysdate) THEN '1' "
				+ "	    	/* RPVs com convenio (processada no tribunal) ddtar + 60 dias */ "
				+ "	    	WHEN s.sidtiposolicitacao = 'RPV' AND (s.sflprocessamento = 'T' AND d.sidesfera in ('E', 'M')) AND (r.ddtar + INTERVAL '60' DAY) < TRUNC(sysdate) THEN '1' "
				+ "	    	/* RPVs processada na Vara: Campo especifico: Data do fim do prazo de pagamento. */  "
				+ "	    	WHEN s.sidtiposolicitacao = 'RPV' AND s.sflprocessamento = 'V' AND TRUNC(s.ddtfimprazopagamento) < TRUNC(sysdate) THEN '1' "
				+ "	    	/* PRECATORIO /* "
				+ "	    	WHEN s.sidtiposolicitacao = 'PRE' AND r.ianovencimento < EXTRACT(YEAR FROM SYSDATE) THEN '1' "
				+ "	    	ELSE '0' "
				+ "		END "
				+ "     ELSE '0' "
				+ "	END AS SFLPRAZOPAGAMENTOVENCIDO  "
				+ "FROM gep.TBREQUISICAOPAGAMENTO r  "
				+ "inner JOIN GEP.TBSOLICITACAO S ON "
				+ "	(S.IIDSOLICITACAO = r.IIDSOLICITACAO) "
				+ "inner JOIN GEP.TBSITUACAO e ON "
				+ "	(e.IIDSITUACAO = s.IIDSITUACAO) "
				+ "inner JOIN GEP.TBDEVEDOR d ON "
				+ "	(d.IIDDEVEDOR = s.IIDDEVEDOR) "
				+ "inner JOIN gep.tbpessoa p ON "
				+ "	(p.IIDPESSOA = d.IIDPESSOA) "
				+ "INNER JOIN gep.tbprocesso k ON "
				+ "	(s.iidprocesso = k.IIDPROCESSO) "
				+ "INNER JOIN GEP.TBPESSOAJURIDICA pj ON "
				+ "	(pj.IIDPESSOA = p.IIDPESSOA) "
				+ "INNER JOIN gep.TBBENEFICIO b ON "
				+ "	(b.IIDSOLICITACAO = s.iidsolicitacao) "
				+ "INNER JOIN gep.VWGPRECVARA v ON "
				+ "	(v.IIDVARA = k.INRVARA) "
				+ "INNER JOIN 	GEP.TBBENEFICIARIO bf ON (bf.IIDBENEFICIARIO = b.IIDBENEFICIARIO) "
				+ "INNER JOIN gep.tbpessoa n ON (n.IIDPESSOA  = bf.IIDPESSOA) "
				+ "INNER JOIN gep.TBPESSOAFISICA pfb ON (pfb.IIDPESSOA = bf.IIDPESSOA) "
				+ "LEFT  JOIN gep.TBPESSOAJURIDICA pjb ON (pjb.IIDPESSOA = bf.IIDPESSOA) "
				+ "WHERE "
				+ "1 = 1  "
				+ "AND e.SNMSITUACAO = 'AUTUADA' "
				+ "AND s.SFLFINALIZADA = 1 "
				+ "AND trunc(r.DDTAUTUACAO) BETWEEN to_date(?) AND to_date(?)"
				+ "AND d.SIDESFERA NOT IN ('M','E','F1') "
				+ "";
	
		PreparedStatement stmtProcesso;
		try {
			stmtProcesso = con.prepareStatement(queryProcesso);
			stmtProcesso.setString(1, datini);
			stmtProcesso.setString(2, datfim);
			logger.debug("##################### ");
			logger.debug(" DDTAUTUACAO between " + datini + " and " + datini);
			ResultSet rs = stmtProcesso.executeQuery();
			
            while (rs.next()) {
            	RequisicaoPagamento rp = new RequisicaoPagamento(
                        rs.getDouble("IIDSOLICITACAO"),
                        rs.getString("SNRREQUISICAO"),
                        rs.getString("SNRPROCESSO"),
                        rs.getInt("IANOVENCIMENTO"));
            	
            	requisicaoPagamento.add(rp);
            }
            
            return requisicaoPagamento;
            
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return requisicaoPagamento;
        
        }

	
}
