

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * T‰m‰ luokka on ohjelman p‰‰luokka, josta peli k‰ynistyy. <p> Se koostuu konstruktorista ja main-metodista, jossa kutsutaan Main olion ja samalla
 * k‰ynistet‰‰n pelin, joka on kootu olion konstruktoriin.
 * 
 * @author Ekaterina Kirienko
 * @version 1.0
 * @since 17-4-2019
 */

public class Main extends JFrame {
	
	/**
	 * T‰m‰ on p‰‰luokan konstruktori, joka vastaa peli-ikkunasta ja siit‰, ett‰ Pelikentta olio olisi toteuttu, joten peli k‰ynisytisi.
	 * 
	 */
	
	public Main() {
		setTitle("P‰‰ttyv‰ tarina");
		ImageIcon kuvakke = new ImageIcon("head.png");
		setIconImage(kuvakke.getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(new Pelikentta());
		/**
		 * t‰m‰ metodi m‰‰ritt‰‰ peli-ikkunan koko. <p>
		 * x = 480, y = 480 ja taskbar height ~25
		 */
		setSize(480,505); 
		setLocation(400,125);
		setVisible(true);
	}

	/**
	 * t‰m‰ metodi luo uuden Main olion ja pelin suoristus alkaa siit‰
	 * 
	 * @param args  ei ole k‰ytetty
	 */
	public static void main(String[] args){		
		Main main = new Main();

	}



}
