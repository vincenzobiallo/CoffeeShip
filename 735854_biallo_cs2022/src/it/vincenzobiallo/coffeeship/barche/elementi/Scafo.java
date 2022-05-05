package it.vincenzobiallo.coffeeship.barche.elementi;

public enum Scafo {

	MONOSCAFO(""),
	MULTISCAFO("");
	
	private String descrizione;
	
	Scafo(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
}
