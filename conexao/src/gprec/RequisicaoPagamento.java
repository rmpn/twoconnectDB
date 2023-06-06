package gprec;


import java.util.Objects;


public class RequisicaoPagamento {

	private Double iidsolicitacao;
	private String snrrequicao;
	private String snrprocesso;         
	private Integer anovencimento;
		
	public RequisicaoPagamento(double iidsolicitacao, String snrrequicao, String snrprocesso, int anovencimento) {
		// TODO Auto-generated constructor stub
	}

	public Double getIidsolicitacao() {
		return iidsolicitacao;
	}

	public void setIidsolicitacao(Double iidsolicitacao) {
		this.iidsolicitacao = iidsolicitacao;
	}

	public String getSnrrequicao() {
		return snrrequicao;
	}

	public void setSnrrequicao(String snrrequicao) {
		this.snrrequicao = snrrequicao;
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
		return Objects.hash(iidsolicitacao, snrprocesso, snrrequicao);
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
				&& Objects.equals(snrrequicao, other.snrrequicao);
	}

	@Override
	public String toString() {
		return "RequicaoPagamento [iidsolicitacao=" + iidsolicitacao + ", snrrequicao=" + snrrequicao + ", snrprocesso="
				+ snrprocesso + "]";
	}

	

	
	

	}
