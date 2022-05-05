package it.vincenzobiallo.coffeeship.barche.elementi;

public enum Deriva {
	
	PERNO("a Perno", ""),
	RETRATTILE("Retrattile", "");
	
	private String nome;
	private String descrizione;
	
	Deriva(String nome, String descrizione) {
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
