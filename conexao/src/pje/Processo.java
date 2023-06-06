package pje;

import java.util.Objects;

public class Processo {
	
	private String nrprocesso; 
	private String docrfbautor;
	private String nomeautor;
	private String docrfbreu;
	private String nomereu;
	
	
	public Processo(String nrprocesso, String docrfbautor, String nomeautor, String docrfbreu, String nomereu) {
		super();
		this.nrprocesso = nrprocesso;
		this.docrfbautor = docrfbautor;
		this.nomeautor = nomeautor;
		this.docrfbreu = docrfbreu;
		this.nomereu = nomereu;
	}
	
	
	
	
	public String getNrprocesso() {
		
		return nrprocesso;
	}




	public void setNrprocesso(String nrprocesso) {
		this.nrprocesso = nrprocesso; 
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
		return Objects.hash(docrfbautor, docrfbreu, nomeautor, nomereu, nrprocesso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		return Objects.equals(docrfbautor, other.docrfbautor) && Objects.equals(docrfbreu, other.docrfbreu)
				&& Objects.equals(nomeautor, other.nomeautor) && Objects.equals(nomereu, other.nomereu)
				&& Objects.equals(nrprocesso, other.nrprocesso);
	}



	@Override
	public String toString() {
		return "Processo [nrprocesso=" + nrprocesso + ", nomeautor=" + nomeautor + ", nomereu=" + nomereu + "]";
	}

	

}
