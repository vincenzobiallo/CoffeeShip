package it.vincenzobiallo.coffeeship.barche.elementi;

public enum Chiglia {
	
	LUNGA("Lunga", ""),
	SEMILUNGA("Semilunga", ""),
	PINNA("a Pinna", ""),
	BULBO("a Bulbo", ""),
	ROLLIO("a Rollio", "");
	
	private String nome;
	private String descrizione;
	
	Chiglia(String nome, String descrizione) {
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
