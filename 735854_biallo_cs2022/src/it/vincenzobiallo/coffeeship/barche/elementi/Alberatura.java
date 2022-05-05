package it.vincenzobiallo.coffeeship.barche.elementi;

public enum Alberatura {
	
	SLOOP_ARMO("Sloop con Armo", ""),
	SLOOP_FRAZIONATO("Sloop Frazionato", ""),
	CUTTER("Cutter", ""),
	KETCH("Ketch", ""),
	GOLETTA("Goletta", ""),
	IOLE("Iole", "");
	
	private String nome;
	private String descrizione;
	
	Alberatura(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

}
