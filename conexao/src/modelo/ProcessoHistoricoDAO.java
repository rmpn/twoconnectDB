package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ProcessoHistoricoDAO {

	public Connection con;

	static Logger logger = LogManager.getLogger(ProcessoHistoricoDAO.class.getName());

	public ProcessoHistoricoDAO(Connection con) {
		this.con = con;
	}

	/**
	 * salvar listaProcessos atualizada no banco
	 * 
	 * @param listaProcessos - lista com os dados atualizados
	 * @param usuarioEnvio   - usuario que enviou para historico
	 * @return void
	 */
	public void salvarLista(List<ProcessoHistorico> listaProcessos, String usuarioEnvio) {
		Iterator<ProcessoHistorico> it2 = listaProcessos.iterator();

		logger.info("### Salvando lista no banco. " + listaProcessos.size());
		while (it2.hasNext()) {
			ProcessoHistorico ph = it2.next();

			if (this.con == null) {
				logger.debug("A conexão é NULA!!");
				System.exit(0);
			}

			String queryProcesso = "UPDATE cndt.xml_processo " + "SET ind_carga = ? " + " WHERE		numero = ?"
					+ "			AND digito = ?" + "			AND ano = ?" + "			AND orgao = ?"
					+ "			AND tribunal = ?" + "			AND vara = ?" + "			AND cpf_resp_info = ?"
					+ "			AND id_parte = ?" + "			AND ind_carga = 0 ";

			String queryProcessoHistorico = "UPDATE cndt.xml_processo_historico "
					+ "SET ind_carga = ?, descricao_erro = ?, usuario_envio = ? " + " WHERE		numero = ?"
					+ "			AND digito = ?" + "			AND ano = ?" + "			AND orgao = ?"
					+ "			AND tribunal = ?" + "			AND vara = ?" + // " AND operacao = ?" + " AND
																				// codigo_situacao = ?" +
					"			AND cpf_resp_info = ?" + "			AND id_parte = ?" + "			AND ind_carga = 0 ";

			PreparedStatement stmtProcesso;
			PreparedStatement stmtProcessoHistorico;

			try {
				this.con.setAutoCommit(false);
				// atualiza��o em xml_processo
				stmtProcesso = this.con.prepareStatement(queryProcesso);
				stmtProcesso.setInt(1, ph.getInd_carga());
				stmtProcesso.setString(2, ph.getNumero());
				stmtProcesso.setString(3, ph.getDigito());
				stmtProcesso.setString(4, ph.getAno());
				stmtProcesso.setString(5, ph.getOrgao());
				stmtProcesso.setString(6, ph.getTribunal());
				stmtProcesso.setString(7, ph.getVara()); // stmt.setString(9, ph.getOperacao()); stmt.setString(10,
															// ph.getCodigo_situacao());
				stmtProcesso.setString(8, ph.getCpf_resp_info());
				stmtProcesso.setDouble(9, ph.getId_parte());
				stmtProcesso.execute();

				logger.debug("##################### ");
				logger.debug(" numero: " + ph.getNumero());
				logger.debug(" digito: " + ph.getDigito());
				logger.debug(" ano: " + ph.getAno());
				logger.debug(" vara: " + ph.getVara());
				logger.debug(" operacao: " + ph.getOperacao());
				logger.debug(" situacao: " + ph.getCodigo_situacao());
				logger.debug(" id_parte: " + ph.getId_parte());
				logger.debug(" cpf_resp: " + ph.getCpf_resp_info());
				logger.debug(" flag EnviarProcesso " + ph.getEnviarProcesso());

				// atualiza��o em xml_processo_historico
				stmtProcessoHistorico = this.con.prepareStatement(queryProcessoHistorico);
				stmtProcessoHistorico.setInt(1, ph.getInd_carga());
				stmtProcessoHistorico.setString(2, ph.getDescricaoErro());
				stmtProcessoHistorico.setString(3, usuarioEnvio);
				stmtProcessoHistorico.setString(4, ph.getNumero());
				stmtProcessoHistorico.setString(5, ph.getDigito());
				stmtProcessoHistorico.setString(6, ph.getAno());
				stmtProcessoHistorico.setString(7, ph.getOrgao());
				stmtProcessoHistorico.setString(8, ph.getTribunal());
				stmtProcessoHistorico.setString(9, ph.getVara()); // stmt.setString(9, ph.getOperacao());
																	// stmt.setString(10, ph.getCodigo_situacao());
				stmtProcessoHistorico.setString(10, ph.getCpf_resp_info());
				stmtProcessoHistorico.setDouble(11, ph.getId_parte());
				stmtProcessoHistorico.execute();

				this.con.commit();
				this.con.setAutoCommit(true);

				stmtProcesso.close();
				stmtProcessoHistorico.close();
//				logger.debug("Comando executado: " + queryProcesso);
//				logger.debug("Comando executado: " + queryProcessoHistorico);
			} catch (SQLException e) {
				logger.error("Erro ao salvar listaProcesso " + e);
			}

			logger.debug("### atualizando processo: ");
			logger.debug(" numero: " + ph.getNumero());
			logger.debug(" digito: " + ph.getDigito());
			logger.debug(" ano: " + ph.getAno());
			logger.debug(" vara: " + ph.getVara());
			logger.debug(" operacao: " + ph.getOperacao());
			logger.debug(" situacao: " + ph.getCodigo_situacao());
			logger.debug(" id_parte: " + ph.getId_parte());
			logger.debug(" cpf_resp: " + ph.getCpf_resp_info());
			logger.debug(" flag EnviarProcesso " + ph.getEnviarProcesso());

		}
	}
}
