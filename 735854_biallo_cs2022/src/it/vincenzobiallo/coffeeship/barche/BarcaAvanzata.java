package it.vincenzobiallo.coffeeship.barche;

import it.vincenzobiallo.coffeeship.barche.elementi.*;
import it.vincenzobiallo.coffeeship.exceptions.BarcaException;

class BarcaAvanzata extends Barca {

	public BarcaAvanzata(String numero_serie, Scafo scafo, Chiglia chiglia, Deriva deriva, Alberatura alberatura, Timone timone) throws BarcaException {
		super(numero_serie, scafo, chiglia, deriva, alberatura, timone);
		super.modello = ModelloBarca.AVANZATA;
	}

	@Override
	protected void setupCabina() {
		super.cabine = new Cabina[2];
		cabine[0] = new Cabina(this, 4, true, 2);
		cabine[1] = new Cabina(this, 2, false, 1);
	}

}
