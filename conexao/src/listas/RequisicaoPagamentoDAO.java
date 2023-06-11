package listas;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.ArquivoTexto;

public class RequisicaoPagamentoDAO {
	
	public Connection con;

	
	static Logger logger = LogManager.getLogger(RequisicaoPagamentoDAO.class.getName());

	public RequisicaoPagamentoDAO(Connection con) {
		this.con = con;
	}

	
public List<RequisicaoPagamento> getReqPag(String datini,  String datfim) {
        
		
	
		List<RequisicaoPagamento> requisicaoPagamento = new ArrayList<RequisicaoPagamento>();
		
		
		RequisicaoPagamento rp;
		
		String p;
		
		
		
		String sqlProcesso = "SELECT s.iidsolicitacao, R.SNRREQUISICAO AS snrrequisicao, "
				+ "				   	k.SNRPROCESSO AS snrprocesso, "
				+ "				   	s.sidtiposolicitacao AS tipo, "
				+ "				   	d.SIDESFERA AS ESFERA, "
				+ "				   	e.SNMSITUACAO AS SNMSITUACAO, "
				+ "				   	to_char(r.DDTAutuacao, 'dd/mm/yyyy') AS ddtautuacao, "
				+ "				   	decode(s.SFLFINALIZADA, 1, 'SIM', 'NAO') AS publicada, "
				+ "				   	decode(b.MVLLIQUIDO,0,nvl(b.MVLLIQUIDOORIGINAL,b.MVLLIQUIDO),b.MVLLIQUIDO)  AS valor, "
				+ "				   	s.SIDTIPOENTE AS ente, "
				+ "				   	substr(pj.SCNPJ, 1, 2)|| '.' || substr(pj.SCNPJ, 3, 3)|| '.' || substr(pj.SCNPJ, 6, 3)|| '/' || substr(pj.SCNPJ, 9, 4)|| '-' || substr(pj.SCNPJ, 13, 2) AS rfbreu, "
				+ "                 pj.SCNPJ as cnpjreu, "  
				+ "				   	p.SNMPESSOA AS nmreu, "
				+ "				   	v.SNMVARA AS vara, "
				+ "				   	(r.IANOVENCIMENTO - to_number(to_char(sysdate, 'yyyy'))) AS prazo, "
				+ "				   	d.SIDESFERA AS esfera, "
				+ "				   	n.SNMPESSOA as nmautor, "
				+ "				   	nvl(pfb.SCPF,pjb.SCNPJ) AS rfbautor, "
				+ "				   	r.IANOVENCIMENTO AS datorc,\r\n"
				+ "				   	CASE WHEN e.iidsituacao NOT in (7) THEN\r\n"
				+ "		CASE\r\n"
				+ "	    	WHEN s.sidtiposolicitacao = 'RPV' AND d.sidesfera = 'F' AND TRUNC(s.ddtfimprazopagamento + INTERVAL '60' DAY) < TRUNC(sysdate) THEN '1'\r\n"
				+ "	    	WHEN s.sidtiposolicitacao = 'RPV' AND (s.sflprocessamento = 'T' AND d.sidesfera in ('E', 'M')) AND (r.ddtar + INTERVAL '60' DAY) < TRUNC(sysdate) THEN '1'\r\n"
				+ "	    	WHEN s.sidtiposolicitacao = 'RPV' AND s.sflprocessamento = 'V' AND TRUNC(s.ddtfimprazopagamento) < TRUNC(sysdate) THEN '1'\r\n"
				+ "	    	WHEN s.sidtiposolicitacao = 'PRE' AND r.ianovencimento < EXTRACT(YEAR FROM SYSDATE) THEN '1'\r\n"
				+ "	    	ELSE '0'\r\n"
				+ "		END\r\n"
				+ "     ELSE '0'\r\n"
				+ "	END AS SFLPRAZOPAGAMENTOVENCIDO\r\n"
				+ "				   FROM gep.TBREQUISICAOPAGAMENTO r   "
				+ "				   inner JOIN GEP.TBSOLICITACAO S ON (S.IIDSOLICITACAO = r.IIDSOLICITACAO)  "
				+ "				   inner JOIN GEP.TBSITUACAO e ON (e.IIDSITUACAO = s.IIDSITUACAO)  "
				+ "				   inner JOIN GEP.TBDEVEDOR d ON (d.IIDDEVEDOR = s.IIDDEVEDOR)  "
				+ "				   inner JOIN gep.tbpessoa p ON (p.IIDPESSOA = d.IIDPESSOA)  "
				+ "				   INNER JOIN gep.tbprocesso k ON (s.iidprocesso = k.IIDPROCESSO)  "
				+ "				   INNER JOIN GEP.TBPESSOAJURIDICA pj ON (pj.IIDPESSOA = p.IIDPESSOA)  "
				+ "				   INNER JOIN gep.TBBENEFICIO b ON (b.IIDSOLICITACAO = s.iidsolicitacao)  "
				+ "				   INNER JOIN gep.VWGPRECVARA v ON (v.IIDVARA = k.INRVARA)  "
				+ "				   INNER JOIN GEP.TBBENEFICIARIO bf ON (bf.IIDBENEFICIARIO = b.IIDBENEFICIARIO)  "
				+ "				   INNER JOIN gep.tbpessoa n ON (n.IIDPESSOA  = bf.IIDPESSOA)  "
				+ "				   INNER JOIN gep.TBPESSOAFISICA pfb ON (pfb.IIDPESSOA = bf.IIDPESSOA)  "
				+ "				   LEFT  JOIN gep.TBPESSOAJURIDICA pjb ON (pjb.IIDPESSOA = bf.IIDPESSOA)  "
				+ "				   WHERE\r\n"
				+ "				   1 = 1   "
				+ "				   AND e.SNMSITUACAO = 'AUTUADA'  "
				+ "				   AND s.SFLFINALIZADA = 1  "
				+ "				   AND trunc(r.DDTAUTUACAO) BETWEEN to_date(?) AND to_date(?) "
				+ "				   AND d.SIDESFERA NOT IN ('M','E','F1') ";

		PreparedStatement stmtProcesso;
		try {
			stmtProcesso = con.prepareStatement(sqlProcesso);
			stmtProcesso.setString(1, datini);
			stmtProcesso.setString(2, datfim);
			logger.info("##################### ");
			logger.info(" Data de autuação entre " + datini + " and " + datfim);
			ResultSet rs = stmtProcesso.executeQuery();
			//
            while (rs.next()) {
            	//
            	p = rs.getString("SNRPROCESSO");
            	p = p.substring(0, 7)+"-"+p.substring(7,9)+"."+p.substring(9,13)+"."+p.substring(13,14)+"."+p.substring(14,16)+"."+p.substring(16,20);
                // System.out.println(rfbDoc(rs.getString("cnpjreu"))+"------"+rfbDoc(rs.getString("rfbautor")));
            	rp = new RequisicaoPagamento(rs.getDouble("IIDSOLICITACAO"),rs.getString("SNRREQUISICAO"),p,rs.getInt("DATORC"),rs.getString("NMREU"),rfbDoc(rs.getString("RFBREU")),
                        rs.getString("NMAUTOR"),rfbDoc(rs.getString("RFBAUTOR")));	
            	requisicaoPagamento.add(rp);
            	ArquivoTexto.gravaArquivo(String.valueOf(rp.toString()));
            }
            
            return requisicaoPagamento;
            
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return requisicaoPagamento;
        
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
	
}
