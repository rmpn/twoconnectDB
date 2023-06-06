package modelo;

public class ProcessoHistorico {

	private Double id_historico;
	private String numero;
	private String digito;
	private String ano;
	private String orgao;
	private String tribunal;
	private String vara;
	private String operacao;
	private String codigo_situacao;
	private String cpf_resp_info;
	private Double id_parte;
	private String descricaoErro;
	private int ind_carga;
	private boolean enviarProcesso;
	private String data_situacao;

	public Double getId_historico() {
		return id_historico;
	}

	public void setId_historico(Double id_historico) {
		this.id_historico = id_historico;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDigito() {
		return digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	public String getTribunal() {
		return tribunal;
	}

	public void setTribunal(String tribunal) {
		this.tribunal = tribunal;
	}

	public String getVara() {
		return vara;
	}

	public void setVara(String vara) {
		this.vara = vara;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getCodigo_situacao() {
		return codigo_situacao;
	}

	public void setCodigo_situacao(String codigo_situacao) {
		this.codigo_situacao = codigo_situacao;
	}

	public String getCpf_resp_info() {
		return cpf_resp_info;
	}

	public void setCpf_resp_info(String cpf_resp_info) {
		this.cpf_resp_info = cpf_resp_info;
	}

	public Double getId_parte() {
		return id_parte;
	}

	public void setId_parte(Double id_parte) {
		this.id_parte = id_parte;
	}

	public String getDescricaoErro() {
		return descricaoErro;
	}

	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = descricaoErro;
	}

	public int getInd_carga() {
		return ind_carga;
	}

	public void setInd_carga(int ind_carga) {
		this.ind_carga = ind_carga;
	}

	public boolean getEnviarProcesso() {
		return enviarProcesso;
	}

	public void setEnviarProcesso(boolean enviarProcesso) {
		this.enviarProcesso = enviarProcesso;
	}

	public String getDataSituacao() {
		return data_situacao;
	}

	public void setDataSituacao(String data) {
		this.data_situacao = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((codigo_situacao == null) ? 0 : codigo_situacao.hashCode());
		result = prime * result + ((cpf_resp_info == null) ? 0 : cpf_resp_info.hashCode());
		result = prime * result + ((digito == null) ? 0 : digito.hashCode());
		result = prime * result + ((id_parte == null) ? 0 : id_parte.hashCode());
		result = prime * result + ind_carga;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((operacao == null) ? 0 : operacao.hashCode());
		result = prime * result + ((orgao == null) ? 0 : orgao.hashCode());
		result = prime * result + ((tribunal == null) ? 0 : tribunal.hashCode());
		result = prime * result + ((vara == null) ? 0 : vara.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessoHistorico other = (ProcessoHistorico) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (codigo_situacao == null) {
			if (other.codigo_situacao != null)
				return false;
		} else if (!codigo_situacao.equals(other.codigo_situacao))
			return false;
		if (cpf_resp_info == null) {
			if (other.cpf_resp_info != null)
				return false;
		} else if (!cpf_resp_info.equals(other.cpf_resp_info))
			return false;
		if (digito == null) {
			if (other.digito != null)
				return false;
		} else if (!digito.equals(other.digito))
			return false;
		if (id_parte == null) {
			if (other.id_parte != null)
				return false;
		} else if (!id_parte.equals(other.id_parte))
			return false;
		// if (ind_carga != other.ind_carga)
		// return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (operacao == null) {
			if (other.operacao != null)
				return false;
		} else if (!operacao.equals(other.operacao))
			return false;
		if (orgao == null) {
			if (other.orgao != null)
				return false;
		} else if (!orgao.equals(other.orgao))
			return false;
		if (tribunal == null) {
			if (other.tribunal != null)
				return false;
		} else if (!tribunal.equals(other.tribunal))
			return false;
		if (vara == null) {
			if (other.vara != null)
				return false;
		} else if (!vara.equals(other.vara))
			return false;
		return true;
	}

	/**
	 * metodo sobrescrito para imprimir apenas processos com erro
	 * 
	 * @return retorna string com os campos do processo enviado
	 */
	@Override
	public String toString() {
		String conteudo = "";

		/*
		 * if(this.descricaoErro.equals(null))
		 * 
		 * else {
		 */
		try {
			if (this.ind_carga == 2) {
				conteudo = // " id_historico= " + this.id_historico +
						"\n numero=" + this.numero + "\n digito=" + this.digito + "\n ano=" + this.ano + "\n orgao="
								+ this.orgao + "\n tribunal=" + this.tribunal + "\n vara=" + this.vara + "\n operacao="
								+ this.operacao + "\n codigo_situacao=" + this.codigo_situacao + "\n cpf_resp_info="
								+ this.cpf_resp_info + "\n id_parte=" + this.id_parte + "\n descricaoErro="
								+ this.descricaoErro + "\n ind_carga;" + this.ind_carga + "\n"
								+ "--------------------------------------------------------\n";
				/* TODO incluir data_situacao */
			}
		} catch (Exception e) {
			conteudo = null;
		}

		return conteudo;
	}

	}
