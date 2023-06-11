package gprec;


import java.util.Objects;


public class RequisicaoPagamento {

	private Double iidsolicitacao;
	private String snrrequisicao;
	private String snrprocesso;         
	private Integer anovencimento;
		
	public RequisicaoPagamento(double iidsolicitacao, String snrrequisicao, String snrprocesso, int anovencimento) {
		
		String p = snrprocesso;
		
		this.iidsolicitacao = iidsolicitacao;
		this.snrrequisicao = snrrequisicao;
		this.snrprocesso = p.substring(0, 7)+"-"+p.substring(7,9)+"."+p.substring(9,13)+"."+p.substring(13,14)+"."+p.substring(14,16)+"."+p.substring(16,20);
		this.anovencimento = anovencimento;
		
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
		return "RequicaoPagamento [iidsolicitacao=" + iidsolicitacao + ", snrrequisicao=" + snrrequisicao + ", snrprocesso="
				+ snrprocesso + "]";
	}

	

	
	

	}
