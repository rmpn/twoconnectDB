package xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import conexao.ConexaoBD;
import modelo.ProcessoHistorico;
import modelo.ProcessoHistoricoDAO;
import util.ArquivoTexto;
import util.Constantes;
import util.EnvioEmail;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class PartesXML {
	private static Connection con;

	
	private static List<ProcessoHistorico> listaProcessos = new ArrayList<ProcessoHistorico>();
	static Logger logger = LogManager.getLogger(ConexaoBD.class.getName());

	public PartesXML(Connection con) {
		PartesXML.con = con;
		listaProcessos.clear();
	}

	
	@SuppressWarnings("finally")
	public String geraXmlPartes(int indCarga, int codSetor) {
		Element partes = new Element("partes");
		// armazena a qtd de partes retornadas
		int qtdPartes = 0;
		// armazena a qtd de processos retornadas
		int qtdProcessos = 0;

		if (con == null) {
			logger.debug("A conexao a© NULA!!");
			System.exit(0);
		}
		try {
			PreparedStatement stmt = con.prepareStatement(
					"" + "SELECT	DISTINCT (p.id_parte), p.cpf, p.cnpj, p.nome_parte" + " FROM	cndt.xml_parte p"
							+ " WHERE	p.id_parte IN" + "					(SELECT	DISTINCT (id_parte)"
							+ " 					FROM cndt.xml_processo" + " 					WHERE ind_carga = ?"
							+ "						 AND NVL(cod_setor, vara) = ? )  " + "ORDER BY p.id_parte");

			stmt.setInt(1, indCarga);
			stmt.setInt(2, codSetor);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Element parte = new Element("parte");
				Element cpf = new Element("cpf");
				Element cnpj = new Element("cnpj");
				Element processos = new Element("processos");

				DecimalFormat fCpf = new DecimalFormat("00000000000");
				fCpf.setMinimumIntegerDigits(11);
				fCpf.setMaximumIntegerDigits(11);
				cpf.setText(String.valueOf(fCpf.format(rs.getDouble("cpf"))));
				DecimalFormat fCnpj = new DecimalFormat("00000000000000");
				fCnpj.setMinimumIntegerDigits(14);
				fCnpj.setMaximumIntegerDigits(14);
				cnpj.setText(String.valueOf(fCnpj.format(rs.getDouble("cnpj"))));

				if (!cpf.getText().equals("00000000000"))
					parte.addContent(cpf);

				if (!cnpj.getText().equals("00000000000000"))
					parte.addContent(cnpj);

				processos = geraXmlProcessos(rs.getDouble("id_parte"), indCarga, codSetor);

				if (processos.getContentSize() > 0) {
					parte.addContent(processos);
					partes.addContent(parte);
				}
			}
			qtdPartes = rs.getRow();
			qtdProcessos = partes.getContentSize();
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			logger.error(e);
		} finally {

			String retorno;
			logger.debug("QDT PARTE COM PROCESSOS CADASTRADOS: " + qtdPartes);

			if (listaProcessos.isEmpty() || qtdPartes == 0 || qtdProcessos == 0) {
				retorno = null;
			} else {
				logger.debug("Tam listaProcessos " + listaProcessos.size());
				// Criando o documento XML (montado)
				Namespace xsiNS = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
				partes.addNamespaceDeclaration(xsiNS);
				//partes.setAttribute(new Attribute("schemaLocation", "./cndt_1_.xsd", xsiNS));

				Document doc = new Document();
				doc.setRootElement(partes);

				//Format format = Format.getPrettyFormat();
				//format.setEncoding("UTF-8");
				XMLOutputter xout = new XMLOutputter();

				try {
					// para arquivo
					String nomeArquivoEnvio = "cndt_envio_"
							+ (new SimpleDateFormat("ddMMyyyy_hhmmss").format(new Date())) + ".xml";
					xout.output(doc, new FileOutputStream(nomeArquivoEnvio));
				} catch (IOException e) {
					logger.error(e);
				}

				retorno = xout.outputString(doc);
			}

			// logger.debug(retorno);
			return retorno;
		}
	}

	
	@SuppressWarnings("finally")
	public static Element geraXmlProcessos(Double parte, int indCarga, int codSetor) {

		// Elemento do XML com a lista de processos.
		Element processos = new Element("processos");

		if (con == null) {
			logger.debug("A conexao a© NULA!!");
			System.exit(0);
		}
		try {
			PreparedStatement stmt = con.prepareStatement("" + "SELECT ph.numero, ph.digito, ph.ano, ph.orgao,"
					+ "     ph.tribunal, ph.vara, ph.operacao, ph.codigo_situacao, ph.cpf_resp_info, "
					+ "     ph.id_parte, NVL(ph.data_situacao, ph.dt_alteracao) data_situacao"
					+ "	FROM cndt.xml_processo ph" + "	WHERE ph.id_parte = ? " + "		AND ph.ind_carga = ? "
					+ "		AND NVL(cod_setor, vara) = ? " + "ORDER BY ph.numero ");

			stmt.setDouble(1, parte);
			stmt.setInt(2, indCarga);
			stmt.setInt(3, codSetor);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String rsOperacao = rs.getString("operacao");

				ProcessoHistorico ph = new ProcessoHistorico();
				DecimalFormat fnumero = new DecimalFormat("########");
				ph.setNumero(String.valueOf(fnumero.format(rs.getDouble("numero"))));
				ph.setDigito(String.valueOf(rs.getInt("digito")));
				ph.setAno(String.valueOf(rs.getInt("ano")));
				ph.setOrgao(String.valueOf(rs.getInt("orgao")));
				ph.setTribunal(String.valueOf(rs.getInt("tribunal")));
				ph.setVara(String.valueOf(rs.getInt("vara")));
				ph.setOperacao(rsOperacao);
				ph.setCodigo_situacao(String.valueOf(rs.getInt("codigo_situacao")));
				DecimalFormat fCpf = new DecimalFormat("00000000000");
				fCpf.setMinimumIntegerDigits(11);
				fCpf.setMaximumIntegerDigits(11);
				ph.setCpf_resp_info(fCpf.format(rs.getDouble("cpf_resp_info")));
				// valor de ind_carga = 1 para consultar na tabela de historico se jï¿½ foi
				// enviado
				ph.setInd_carga(1);
				ph.setDataSituacao(
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(rs.getTimestamp("data_situacao")));

				logger.debug(ph.getDataSituacao());

				// teste para ser ou nao enviado e salvo na lista
				boolean flagEnvioProcesso = true;
				// operacao I
				boolean processoEnviado = consultarEnviado(ph, "I", parte);

				//
				if (rsOperacao.trim().equals("I")) {
					if (processoEnviado) {
						logger.debug("Inclusao, processo nao enviado!!!");
						flagEnvioProcesso = false;
					}
				} else if (rsOperacao.trim().equals("A")) {
					if (!processoEnviado) {
						logger.debug("Atualizaa§a0 alterada para I, processo sera¡ enviado");
						ph.setOperacao("I");
					}
				} else if (rsOperacao.trim().equals("E")) {
					if (!processoEnviado) {
						logger.debug("Exclusao, processo nao enviado!!!");
						flagEnvioProcesso = false;
					}
				}

				ph.setEnviarProcesso(flagEnvioProcesso);

				Element processo = new Element("processo");
				Element numero = new Element("numero");
				Element digito = new Element("digito");
				Element ano = new Element("ano");
				Element orgao = new Element("orgao");
				Element tribunal = new Element("tribunal");
				Element vara = new Element("vara");
				Element operacao = new Element("operacao");
				Element codigo_situacao = new Element("codigo_situacao");
				Element cpf_resp_info = new Element("cpf_resp_info");
				Element enviarProcesso = new Element("enviarProcesso");
				Element dataSituacao = new Element("data_situacao");

				numero.setText(ph.getNumero());
				digito.setText(ph.getDigito());
				ano.setText(ph.getAno());
				orgao.setText(ph.getOrgao());
				tribunal.setText(ph.getTribunal());
				vara.setText(ph.getVara());
				operacao.setText(ph.getOperacao());
				codigo_situacao.setText(ph.getCodigo_situacao());
				cpf_resp_info.setText(ph.getCpf_resp_info());
				enviarProcesso.setText(String.valueOf(flagEnvioProcesso));
				dataSituacao.setText(ph.getDataSituacao());

				processo.addContent(numero);
				processo.addContent(digito);
				processo.addContent(ano);
				processo.addContent(orgao);
				processo.addContent(tribunal);
				processo.addContent(vara);
				processo.addContent(operacao);
				processo.addContent(codigo_situacao);
				processo.addContent(cpf_resp_info);
				processo.addContent(enviarProcesso);
				processo.addContent(dataSituacao);

				// testa se irï¿½ para lista e na0 vai para o XML de envio!!
				// caso de I com I anterior, atualizar historico com ind_catga 1
				// caso de E sem I anterior, atualizar historico com ind_catga 1

				if (flagEnvioProcesso == true) {
					enviarProcesso.setText("true");
					processos.addContent(processo);
				}
				// elemento processo incluido na listaProcessos para
				// processamento
				adicionaProcessoLista(processo, parte, indCarga);
				// remove elemento para na0 constar no XML
				processo.removeChild("enviarProcesso");
			} // fim while resultSet

			stmt.close();
			rs.close();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			return processos;
		}

	}

	/**
	 * adiciona elemento processo do xml na listaProcessos
	 * 
	 * @return void
	 */
	private static void adicionaProcessoLista(Element processo, Double parte, int indCarga) {

		ProcessoHistorico processoHistorico = new ProcessoHistorico();
		// processoHistorico.setId_historico(idhistorico);
		processoHistorico.setNumero(processo.getChildText("numero"));
		processoHistorico.setDigito(processo.getChildText("digito"));
		processoHistorico.setAno(processo.getChildText("ano"));
		processoHistorico.setOrgao(processo.getChildText("orgao"));
		processoHistorico.setTribunal(processo.getChildText("tribunal"));
		processoHistorico.setVara(processo.getChildText("vara"));
		processoHistorico.setOperacao(processo.getChildText("operacao"));
		processoHistorico.setCodigo_situacao(processo.getChildText("codigo_situacao"));
		processoHistorico.setCpf_resp_info(processo.getChildText("cpf_resp_info"));
		processoHistorico.setId_parte(parte);
		processoHistorico.setInd_carga(indCarga);
		processoHistorico.setEnviarProcesso(Boolean.parseBoolean(processo.getChildText("enviarProcesso")));

		listaProcessos.add(processoHistorico);
	}

	/**
	 * atualiza individualmente o indicador de carga dos processos na listaProcessos
	 * 
	 * @return void
	 */
	private static boolean atualizaIndCarga(Element processo, Double idParte, int indCarga) {
		boolean retorno = false;

		ProcessoHistorico processoHistorico = new ProcessoHistorico();
		// processoHistorico.setId_historico(idhistorico);
		processoHistorico.setNumero(processo.getChildText("numero"));
		processoHistorico.setDigito(processo.getChildText("digito"));
		processoHistorico.setAno(processo.getChildText("ano"));
		processoHistorico.setOrgao(processo.getChildText("orgao"));
		processoHistorico.setTribunal(processo.getChildText("tribunal"));
		processoHistorico.setVara(processo.getChildText("vara"));
		processoHistorico.setOperacao(processo.getChildText("operacao"));
		processoHistorico.setCodigo_situacao(processo.getChildText("codigo_situacao"));
		processoHistorico.setCpf_resp_info(processo.getChildText("cpf_resp_info"));
		processoHistorico.setId_parte(idParte);

		// localiza processo na listaProcessos para atualizar
		Iterator<ProcessoHistorico> it = listaProcessos.iterator();
		logger.debug("atualizaIndCarga !!!");
		while (it.hasNext()) {
			ProcessoHistorico processoHistoricoIt = it.next();

			if (processoHistoricoIt.equals(processoHistorico)) {
				processoHistoricoIt.setDescricaoErro(processo.getChildText("descricaoErro"));
				processoHistoricoIt.setInd_carga(indCarga);
				logger.debug(" numero: " + processoHistorico.getNumero());
				logger.debug(" digito: " + processoHistorico.getDigito());
				logger.debug(" ano: " + processoHistorico.getAno());
				logger.debug(" cpf_resp: " + processoHistorico.getCpf_resp_info());
				logger.debug(" id_parte: " + idParte);
				retorno = true;
			}
		}
		return retorno;
	}

	/**
	 * atualiza o indicador de carga de todos processos na listaProcessos
	 * 
	 * @return void
	 */
	private static void atualizaIndCargaTodos(int indCarga) {
		Iterator<ProcessoHistorico> it = listaProcessos.iterator();

		while (it.hasNext()) {
			ProcessoHistorico processoHistorico = (ProcessoHistorico) it.next();
			processoHistorico.setInd_carga(indCarga);
		}
	}

	/**
	 * Faz o tratamento da resposta do WebService do CNDT no TST.
	 * 
	 * @param strXML
	 * @param usuarioEnvio - para envio do resultado do processamento
	 * @param codSetor
	 */
	public static void processarResultado(String strXML, String usuarioEnvio, int codSetor) {
		// conversao da string para XML
		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(strXML);

		Document doc = null;
		Element resultado = null;
		Element sucesso = null;
		Element descricaoErroGeral = null;
		Element qtdPartes = null;
		Element qtdProcessos = null;
		Element partes = null;
		List<Element> lstPartes = null;

		EnvioEmail emailResultadoWS = new EnvioEmail();

		try {
			// String para estrutura do XML
			doc = builder.build(in);

			resultado = doc.getRootElement();
			sucesso = resultado.getChild("sucesso");
			logger.debug("elemento sucesso: " + sucesso.getText());

			atualizaIndCargaTodos(Constantes.INDICADOR_CARGA_ENVIADO);
			if (sucesso.getText().equals("true")) {
				logger.debug("sucesso true");
				// atualizaIndCargaTodos(Constantes.INDICADOR_CARGA_ENVIADO);
				logger.info("atualizado todos indicadores de carga com SUCESSO");
				// Email de todos processados com sucesso
				String mensagem = "Todas as partes do XML processadas com sucesso. Foram enviados "
						+ listaProcessos.size() + " pelo usua¡rio " + usuarioEnvio + " da vara/setor " + codSetor + ".";
				emailResultadoWS.setAssunto("Resposta envio CNDT");
				emailResultadoWS.setMensagem(mensagem);
				emailResultadoWS.setAddTo(usuarioEnvio);
				emailResultadoWS.enviar();
				/* Grava arquivo texto com resposta do webservices */
				ArquivoTexto.gravaArquivo(mensagem);
			} else {
				descricaoErroGeral = resultado.getChild("descricaoErroGeral");
				qtdPartes = resultado.getChild("qtdPartes");
				qtdProcessos = resultado.getChild("qtdProcessos");
				logger.info(" ERRO GERAL(processaResultado): " + descricaoErroGeral.getText());
				logger.info(" PARTES COM ERRO: " + qtdPartes.getText());
				logger.info(" PROCESSOS COM ERRO: " + qtdProcessos.getText());

				/* Grava arquivo texto com resposta do webservices */
				ArquivoTexto.gravaArquivo(descricaoErroGeral.getText());

				// iteracao para acessar as partes com erro
				partes = (Element) resultado.getChild("partes");
				lstPartes = partes.getChildren();
				Iterator<Element> itrPartes = lstPartes.iterator();

				logger.debug("### Atualizaa§a0 indicador carga ERRO");

				while (itrPartes.hasNext()) {
					Element parte = (Element) itrPartes.next();
					String cpf = null;
					String cnpj = null;
					try {
						cpf = parte.getChild("cpf").getText();
						logger.debug(" CPF: " + cpf);
					} catch (NullPointerException e) {
						logger.error("CPF nulo: " + e);
					}
					try {
						cnpj = parte.getChild("cnpj").getText();
						logger.debug(" CNPJ: " + cnpj);
					} catch (NullPointerException e) {
						logger.debug("CNPJ nulo: " + e);
					}

					// iteracao para acessar aos processos com erros
					Element processos = (Element) parte.getChild("processos");
					List<Element> lstProcessos = processos.getChildren();
					Iterator<Element> itrProcessos = lstProcessos.iterator();

					while (itrProcessos.hasNext()) {
						Element processo = (Element) itrProcessos.next();
						Double idParte = null;
						idParte = consultarIdParte(cpf);
						if (idParte == null) {
							idParte = consultarIdParte(cnpj);
						}
						if (idParte != null) {
							// atualizacao da lista de processo (ind_carga)
							if (atualizaIndCarga(processo, idParte, Constantes.INDICADOR_CARGA_ERRO)) {
								logger.info("indicador carga atualizado! idParte=" + idParte);
							} else {
								logger.error(
										"processo na0 encontrado na lista para atualizaa§a0 do indicador de carga! idParte="
												+ idParte);
							}
						}
						if (idParte == null) {
							logger.error("Id_parte nï¿½o encontrado");
						}
					}
				} // fim while itr partes

				// Email dos processamentos com erros para o nosso SUPORTE
				emailResultadoWS.setAssunto("Resposta envio CNDT (setor: " + codSetor + ")" + " - ERRO");
				String conteudoMensagem1 = "Usua¡rio: " + usuarioEnvio + " - vara/setor: " + codSetor + "\n"
						+ "Partes com erros: " + qtdPartes.getText() + " - processos com erros: "
						+ qtdProcessos.getText() + "\n\n" + listaProcessos.toString(); // mï¿½todo toString
																						// p/ lista sï¿½
																						// Processos com
																						// erro
				emailResultadoWS.setMensagem(conteudoMensagem1);
				emailResultadoWS.setAddTo("cndt@trt16.jus.br");
				emailResultadoWS.enviar();

				// Email dos processamentos com erros para o nosso USUARIO
				emailResultadoWS.setAssunto("Resposta envio CNDT - ERRO");
				String conteudoMensagem2 = "Usua¡rio: " + usuarioEnvio + " - vara/setor: " + codSetor + "\n"
						+ "Partes com erros: " + qtdPartes.getText() + " - processos com erros: "
						+ qtdProcessos.getText() + ". Favor entrar em contato com a Informa¡tica.\n\n"
						+ listaProcessos.toString(); // mï¿½todo toString p/ lista
														// sï¿½ Processos com erro
				emailResultadoWS.setMensagem(conteudoMensagem2);
				emailResultadoWS.setAddTo(usuarioEnvio);
				emailResultadoWS.enviar();

			} // fim else erro

			// salvar arquivo xml de retorno do web services TST
			//Format format = Format.getPrettyFormat();
			//format.setEncoding("UTF-8");
			XMLOutputter xout = new XMLOutputter();
			// xout.output(doc, System.out);
			String nomeArquivoRetorno = "cndt_resposta_" + (new SimpleDateFormat("ddMMyyyy_hhmmss").format(new Date()))
					+ ".xml";
			xout.output(doc, new FileOutputStream(nomeArquivoRetorno));

		} catch (JDOMException e) {
			logger.error("Descria§a0 erro: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Descria§a0 erro: " + e.getMessage());
		}
	}

	public static void salvar(String usuarioEnvio) {
		ProcessoHistoricoDAO phDAO = new ProcessoHistoricoDAO(con);
		phDAO.salvarLista(listaProcessos, usuarioEnvio);
		listaProcessos.clear();
	}

	// TODO mover este mï¿½todo para pacote DAO do modelo de Partes
	public static Double consultarIdParte(String cpfCnpj) {
		Double idParte = null;

		if (con == null) {
			logger.debug("A conexao a© NULA!!");
			System.exit(0);
		}
		try {
			PreparedStatement stmt = con
					.prepareStatement("" + "SELECT	DISTINCT (p.id_parte), p.cpf, p.cnpj, p.nome_parte"
							+ " FROM	cndt.xml_parte p" + " WHERE	p.cpf = ? OR p.cnpj = ?");

			stmt.setString(1, cpfCnpj);
			stmt.setString(2, cpfCnpj);

			ResultSet rs = stmt.executeQuery();

			if (rs.next())
				idParte = rs.getDouble("id_parte");

			stmt.close();
			rs.close();

		} catch (SQLException e) {
			logger.error(e);
		}

		return idParte;
	}

	// TODO mover este mï¿½todo para pacote DAO do modelo de Partes
	public static boolean consultarEnviado(ProcessoHistorico p, String operacao, Double idParte) {
		boolean resposta = false;

		if (con == null) {
			logger.debug("A conexao NULA!!");
			System.exit(0);
		}

		try {
			String query = "SELECT * " + " FROM cndt.xml_processo_historico ph " + " WHERE ph.numero = ? "
					+ "     AND ph.digito = ? " + "     AND ph.ano = ? " + "     AND ph.vara = ? "
					+ "     AND ph.operacao = ? " + "     AND ph.id_parte = ? " + "     AND ph.ind_carga = ? ";
			if (p.getOperacao().equals("I")) // operacao informada
				query = query + " AND ph.codigo_situacao = ? ";

			PreparedStatement stmt = con.prepareStatement(query);

			stmt.setString(1, p.getNumero());
			stmt.setString(2, p.getDigito());
			stmt.setString(3, p.getAno());
			stmt.setString(4, p.getVara());
			stmt.setString(5, operacao);
			stmt.setDouble(6, idParte);
			// setado para ind_carga 1 no mï¿½todo geraXmlProcessos!
			stmt.setInt(7, p.getInd_carga());
			if (p.getOperacao().equals("I")) // operacao informada
				stmt.setString(8, p.getCodigo_situacao());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				logger.debug(" idHistorico " + rs.getDouble("id_historico") + " PROCESSO Ja� ENVIADO!!!");
				resposta = true;
			}
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			logger.error(e);
		}

		return resposta;
	}


	public static String enviarXML(String strXML, String usuarioEnvio, String senhaEnvio) {
		// TODO Auto-generated method stub
		return null;
	}

}
