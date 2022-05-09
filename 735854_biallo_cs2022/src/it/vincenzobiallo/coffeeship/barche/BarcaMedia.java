package it.vincenzobiallo.coffeeship.barche;

import it.vincenzobiallo.coffeeship.barche.elementi.*;
import it.vincenzobiallo.coffeeship.exceptions.BarcaException;

class BarcaMedia extends Barca {

	public BarcaMedia(String numero_serie, Scafo scafo, Chiglia chiglia, Deriva deriva, Alberatura alberatura, Timone timone) throws BarcaException {
		super(numero_serie, scafo, chiglia, deriva, alberatura, timone);
		super.modello = ModelloBarca.MEDIA;
	}

	@Override
	protected void setupCabina() {
		super.cabine = new Cabina[1];
		cabine[0] = new Cabina(this, 2, true, 1);
	}

}
