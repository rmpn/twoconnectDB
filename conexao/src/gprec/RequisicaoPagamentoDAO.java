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
    
	
	
}
