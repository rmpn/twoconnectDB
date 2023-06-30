package listas;


import java.util.Objects;


public class RequisicaoPagamento {

	private Double iidsolicitacao;
	private String snrrequisicao;
	private String snrprocesso;         
	private Integer anovencimento;
	private String nmautor;
	private String rfbautor;
	private String nmexecutado;
	private String rfbexecutado;
	private String nmreu;
	private String rfbreu;
	
		
	public RequisicaoPagamento(double iidsolicitacao, String snrrequisicao, String snrprocesso, Integer anovencimento, String nmautor,String rfbautor,String nmreu,String rfbreu, String nmexecutado, String rfbexecutado ) {
                                
		
		
        this.iidsolicitacao = iidsolicitacao;
		this.snrrequisicao = snrrequisicao;
		this.snrprocesso = snrprocesso;
		this.anovencimento = anovencimento;
		this.nmautor = nmautor;
		this.rfbautor = rfbautor;
		this.nmreu = nmreu;
		this.rfbreu = rfbreu;
		this.nmexecutado = nmexecutado;
		this.rfbexecutado = rfbexecutado;
		
	}

	public String getNmexecutado() {
		return nmexecutado;
	}

	public void setNmexecutado(String nmexecutado) {
		this.nmexecutado = nmexecutado;
	}

	public String getRfbexecutado() {
		return rfbexecutado;
	}

	public void setRfbexecutado(String rfbexecutado) {
		this.rfbexecutado = rfbexecutado;
	}

	public Double getIidsolicitacao() {
		return iidsolicitacao;
	}

	public void setIidsolicitacao(Double iidsolicitacao) {
		this.iidsolicitacao = iidsolicitacao;
	}

	public String getSnrrequisicao() {
		return snrrequisicao;
	}

	public void setSnrrequisicao(String snrrequicao) {
		this.snrrequisicao = snrrequicao;
	}

	public String getSnrprocesso() {
		return snrprocesso;
	}

	public void setSnrprocesso(String snrprocesso) {
		this.snrprocesso = snrprocesso;
	}

	
	public Integer getAnovencimento() {
		return anovencimento;
	}

	public void setAnovencimento(Integer anovencimento) {
		this.anovencimento = anovencimento;
	}

	
	
	public String getNmautor() {
		return nmautor;
	}

	public void setNmautor(String nmautor) {
		this.nmautor = nmautor;
	}

	public String getRFFBautor() {
		return rfbautor;
	}

	public void setRFBautor(String rfbautor) {
		this.rfbautor = rfbautor;
	}

	public String getNmreu() {
		return nmreu;
	}

	public void setNmreu(String nmreu) {
		this.nmreu = nmreu;
	}

	public String getRFBreu() {
		return rfbreu;
	}

	public void setRFBreu(String rfbreu) {
		this.rfbreu = rfbreu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(iidsolicitacao, snrprocesso, snrrequisicao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequisicaoPagamento other = (RequisicaoPagamento) obj;
		return Objects.equals(iidsolicitacao, other.iidsolicitacao) && Objects.equals(snrprocesso, other.snrprocesso)
				&& Objects.equals(snrrequisicao, other.snrrequisicao);
	}

	@Override
	public String toString() {
		return (this.iidsolicitacao + ";" + this.snrrequisicao + ";" + this.snrprocesso + ";" +	this.anovencimento + ";" +	this.nmautor + ";" + this.rfbautor + ";" +	this.nmreu + ";" +	this.rfbreu + ";" +	this.nmexecutado + ";" + this.rfbexecutado) ;
	}

	

	
	

	}
