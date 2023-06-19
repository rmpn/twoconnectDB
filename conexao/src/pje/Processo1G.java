package pje;

import java.util.Objects;

public class Processo1G {
	
	private Integer idprocesso;
	private String nrprocesso; 
	private String nomeautor;
	private String docrfbautor;
	private String nomereu;
	private String docrfbreu;
	
	public Processo1G(Integer idprocesso, String nrprocesso, String nomeautor, String docrfbautor, String nomereu,
			String docrfbreu) {
		super();
		this.idprocesso = idprocesso;
		this.nrprocesso = nrprocesso;
		this.nomeautor = nomeautor;
		this.docrfbautor = docrfbautor;
		this.nomereu = nomereu;
		this.docrfbreu = docrfbreu;
	}

	public Integer getIdprocesso() {
		return idprocesso;
	}

	public void setIdprocesso(Integer idprocesso) {
		this.idprocesso = idprocesso;
	}

	public String getNrprocesso() {
		return nrprocesso;
	}

	public void setNrprocesso(String nrprocesso) {
		this.nrprocesso = nrprocesso;
	}

	public String getNomeautor() {
		return nomeautor;
	}

	public void setNomeautor(String nomeautor) {
		this.nomeautor = nomeautor;
	}

	public String getDocrfbautor() {
		return docrfbautor;
	}

	public void setDocrfbautor(String docrfbautor) {
		this.docrfbautor = docrfbautor;
	}

	public String getNomereu() {
		return nomereu;
	}

	public void setNomereu(String nomereu) {
		this.nomereu = nomereu;
	}

	public String getDocrfbreu() {
		return docrfbreu;
	}

	public void setDocrfbreu(String docrfbreu) {
		this.docrfbreu = docrfbreu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idprocesso, nrprocesso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo1G other = (Processo1G) obj;
		return Objects.equals(idprocesso, other.idprocesso) && Objects.equals(nrprocesso, other.nrprocesso);
	}

	@Override
	public String toString() {
		return "Processo1G [idprocesso=" + idprocesso + ", nrprocesso=" + nrprocesso + ", nomeautor=" + nomeautor
				+ ", docrfbautor=" + docrfbautor + ", nomereu=" + nomereu + ", docrfbreu=" + docrfbreu + "]";
	}
	
	
	
	
}
