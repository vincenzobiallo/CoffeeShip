package it.vincenzobiallo.coffeeship.barche.elementi;

public enum Timone {
	
	INTEGRATO("Integrato", ""),
	SKEG("Skeg", ""),
	SOSPESO("Sospeso", ""),
	DOPPIO("Doppio", "");
	
	private String nome;
	private String descrizione;
	
	Timone(String nome, String descrizione) {
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
