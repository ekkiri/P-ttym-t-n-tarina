
/**
 * 
 * T‰m‰ luokka luo positiivisten kokonaislukujen pari x:sta ja y:sta m‰‰ritelty pikseleiss‰. <p> 
 * Niist‰ pareista on tehty ArrayList luokassa Pelikentta, joka s‰ilytt‰‰ kivien koordinaatit, joten kivet pysyisiv‰t paikalla
 * 
 * @author Ekaterina Kirienko
 * @version 1.0
 * @since 17-4-2019
 *
 */
public class KiviKoord {
	
	/**
	 * koordinatin x arvo kokonaisluvuna
	 */
	private int x;
	/**
	 * koordinatin y arvo kokonaisluvuna
	 */
	private int y;

	/**
	 * konstruktoti rakentaa kahdesta luvusta lukuparin
	 * @param x positiivinen kokonaisluku pikseleiss‰
	 * @param y positiivinen kokonaisluku pikseleiss‰
	 */
	public KiviKoord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * ota ensimm‰inen arvo lukuparista eli x ja palauttaa sen
	 * @return koordinaatin x arvo
	 */
	public int getX() {
		return x;
	}
	/**
	 * ota toinen arvo lukuparista eli y ja palauttaa sen
	 * @return koordinaatin y arvo
	 */
	public int getY() {
		return y;
	}
	
}
