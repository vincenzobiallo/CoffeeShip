package it.vincenzobiallo.coffeeship.barche;

class Cabina {
	
	private Barca barca;
	private int letti;
	private boolean cucina;
	private int bagni;
	
	Cabina(Barca barca, int numero_letti, boolean cucina_presente, int numero_bagni) {
		this.barca = barca;
		this.letti = numero_letti;
		this.cucina = cucina_presente;
		this.bagni = numero_bagni;
	}
	
	public Barca getBarca() {
		return barca;
	}
	
	public int getNumeroLetti() {
		return letti;
	}
	
	public boolean possiedeCucina() {
		return cucina;
	}
	
	public int getNumeroBagni() {
		return bagni;
	}

}
