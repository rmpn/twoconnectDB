package pje;

import java.util.Objects;

public class Processo2G {
	
	private Integer idprocesso;
	private String nrprocesso; 
	private String nrprocesso_ref;
	private String docrfbautor;
	private String nomeautor;
	private String docrfbreu;
	private String nomereu;
	
	
	public Processo2G(Integer idprocesso, String nrprocesso, String nrprocesso_ref, String docrfbautor, String nomeautor, String docrfbreu, String nomereu) {
		super();
		this.idprocesso = idprocesso;
		this.nrprocesso = nrprocesso;
		this.nrprocesso_ref = nrprocesso_ref;
		this.docrfbautor = docrfbautor;
		this.nomeautor = nomeautor;
		this.docrfbreu = docrfbreu;
		this.nomereu = nomereu;
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


	public String getNrprocesso_ref() {
		return nrprocesso_ref;
	}


	public void setNrprocesso_ref(String nrprocesso_ref) {
		this.nrprocesso_ref = nrprocesso_ref;
	}



	public String getDocrfbautor() {
		return docrfbautor;
	}


	public void setDocrfbautor(String docrfbautor) {
		this.docrfbautor = docrfbautor;
	}


	public String getNomeautor() {
		return nomeautor;
	}


	public void setNomeautor(String nomeautor) {
		this.nomeautor = nomeautor;
	}




	public String getDocrfbreu() {
		return docrfbreu;
	}




	public void setDocrfbreu(String docrfbreu) {
		
		
		
		this.docrfbreu = docrfbreu;
	}




	public String getNomereu() {
		return nomereu;
	}




	public void setNomereu(String nomereu) {
		this.nomereu = nomereu;
	}




	
	@Override
	public int hashCode() {
		return Objects.hash(docrfbautor, docrfbreu, idprocesso, nomeautor, nomereu, nrprocesso);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo2G other = (Processo2G) obj;
		return Objects.equals(docrfbautor, other.docrfbautor) && Objects.equals(docrfbreu, other.docrfbreu)
				&& Objects.equals(idprocesso, other.idprocesso) && Objects.equals(nomeautor, other.nomeautor)
				&& Objects.equals(nomereu, other.nomereu) && Objects.equals(nrprocesso, other.nrprocesso);
	}




	@Override
	public String toString() {
		return "Processo [nrprocesso=" + this.nrprocesso + ", poloa=" + this.nomeautor + ", rfbpoloa=" + this.docrfbautor +", polob=" + this.nomereu + ", rfbpolob=" + this.docrfbreu + "]";
	}

	

}
