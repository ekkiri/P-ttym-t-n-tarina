import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Tämä luokka luo pelin. Se sisältää kaikki tarvittavat metodit, joten peli
 * käynistäisi ja edeltäisi.
 * Pelin juonina on Michael Ende:n Unendliche Geschichte eli Päättymätön tarina. Eli ihana valkoinen hahmo kerää "ihmeitä", jos hän törmää mustiin <p>
 * ruudukkoihin, niin kamala "Ei Mitään" laajenee.
 * 
 * @author Ekaterina Kirienko
 *
 */
public class Pelikentta extends JPanel implements ActionListener {

	/**
	 * Pelikenttä on neliömuotoinen, joten sen sivun koko on 480 pikselejä
	 */
	private final int SIZE = 480;
	private final int DOT_SIZE = 16;
	/**
	 * Mahdollisien pisteiden pelikentllä summa on 480*480 eli kentän ala jaettuna
	 * yhden yhden pisteen alalla (16*16)
	 */
	private final int ALL_DOTS = 900;
	/**
	 * Kun mato liikkuu vaakasuoralla
	 */
	private Image body;
	/**
	 * Kun mato liikkuu pystysuoralla
	 */
	private Image body180;
	private Image apple;
	private Image kivi;
	private Image head;
	/**
	 * Kun mato liikkuu alaspäin
	 */
	private Image tail;
	/**
	 * Kun mato liikkuu vasemmalle
	 */
	private Image tailL;
	/**
	 * Kun mato liikkuu oikealle
	 */
	private Image tailR;
	/**
	 * Kun mato liikkuu ylöspäin
	 */
	private Image tailU;
	private Image background;
	private int appleX;
	private int appleY;
	private int kiviX;
	private int kiviY;
	/**
	 * Kivet pysyvät paikalla, joten niiden koordinaatit pitäisi säilyttää. Käytän
	 * ArrayList sitä varten.
	 */
	private ArrayList<KiviKoord> kivet = new ArrayList<>();
	private int[] x = new int[ALL_DOTS];
	private int[] y = new int[ALL_DOTS];
	/**
	 * Madon koko
	 */
	private int mato;
	private Timer timer;
	private boolean right = true;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;

	private int score = 0;
	/**
	 * Peli alkaa tasosta yksi, maksimitaso on 5. Taso vaikuttaa madon nopeuteen
	 */
	private int level = 1;

	/**
	 * Luokan konstruktori suorittaa metodia peräkkäin. <p>
	 * Ensiksi lisätään ohjelman kuvia, sitten suoritetaan metodin, josta käytännössä peli alkaa, sitten lisätään olion FieldKeyListener, joka vastaa <p>
	 * siitä, miten mato reagoi, kun nappuloita naputetaan, viimeisenä on metodi setFocusable, joka seuraa pelin etenemistä.
	 */
	public Pelikentta() {
		loadImages();
		initGame();
		addKeyListener(new FieldKeyListener());
		setFocusable(true);

	}

	/**
	 * Luokka luo uudn maton, jonka koko on 3. Mato alkaa liikkumaan pixelistä (64, 64). Samalla käynistyy timer, se märää madon nopeutta. <p>
	 * Sitten kutsutaan metodia "luoda omenan", joka luo uuden omenan pelikentälle. Sen jälkeen kutsutaan metodin "luo ei mitään", joka heti luo <p>
	 * 19 mustaa ruduukkoa, joihin ei saa törmätä
	 */
	public void initGame() {
		mato = 3;
		for (int i = 0; i < mato; i++) {
			x[i] = 64 - i * DOT_SIZE;
			y[i] = 64;
		}

		timer = new Timer(450, this); 
		timer.start();

		createIhme();
		for (int i = 0; i < 20; i++) { 
			createNothing();
		}

	}
    /**
     * Metodi luo ihmeen, eli sen "omenan", jonka madon pitäisi syödä. Metodi Randomin avulla valitsee lukuja 0-29 kertaa yksikön koko (joka on 16), <p>
     *joten kertomalla 30 ja 16 me saadan koko pelin alue. Meidän pitää saada sen omenan näkyviin, joten postetaan yksi(30-1) ja saadan 29.
     */
	public void createIhme() {
		appleX = new Random().nextInt(29) * DOT_SIZE; 
		appleY = new Random().nextInt(29) * DOT_SIZE;
		
		/**
		 * tarkistetaan, että omena ei tulisi "score" pälle ja jos tulee, niin arvotaan luvut uudestaan 
		 */
		if (appleX == kiviX && appleY == kiviY || appleX < 100 && appleY < 100) {
			appleX = new Random().nextInt(29) * DOT_SIZE; 
			appleY = new Random().nextInt(29) * DOT_SIZE;
		}

	}
	/**
     * Metodi luo ei mitään, eli sen mustan ruudukon, jonka madon pitäisi välttää. Metodi Randomin avulla valitsee lukuja 0-29 kertaa yksikön koko 
     * <p>(joka on 16), joten kertomalla 30 ja 16 me saadan koko pelin alue. Meidän pitää saada sen omenan näkyviin, joten postetaan yksi(30-1) <p>
     * ja saadan 29. Lopuksi lisätään saadut arvot ArrayListaan ja säilytetään niitä siihen, joten ne ei häviäis joka kerta, kun pelia piirretään.
     */
	public void createNothing() {
		kiviX = new Random().nextInt(29) * DOT_SIZE;
		kiviY = new Random().nextInt(29) * DOT_SIZE;
		while (kiviX < 100 && kiviY < 100 && appleX == kiviX && appleY == kiviY) {
			kiviX = new Random().nextInt(29) * DOT_SIZE;
			kiviY = new Random().nextInt(29) * DOT_SIZE;
		}

		kivet.add(new KiviKoord(kiviX, kiviY));
	}

	/**
	 * Tämä metodi lataa kuvia ohjelmaan 
	 */
	public void loadImages() {
		ImageIcon iib = new ImageIcon("back.jpg");
		background = iib.getImage();

		ImageIcon iia = new ImageIcon("apple.png");
		apple = iia.getImage();

		ImageIcon iik = new ImageIcon("kivi.png");
		kivi = iik.getImage();

		ImageIcon iih = new ImageIcon("head.png");
		head = iih.getImage();

		ImageIcon iit = new ImageIcon("tail.png");
		tail = iit.getImage();
		ImageIcon iit2 = new ImageIcon("tailL.png");
		tailL = iit2.getImage();
		ImageIcon iit3 = new ImageIcon("tailR.png");
		tailR = iit3.getImage();
		ImageIcon iit4 = new ImageIcon("tailU.png");
		tailU = iit4.getImage();

		ImageIcon iid = new ImageIcon("body.png");
		body = iid.getImage();
		ImageIcon iid2 = new ImageIcon("body180.png");
		body180 = iid2.getImage();

	}

	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		if (inGame) {
			g.setColor(Color.pink);
			Font f2 = new Font("Helvetica", Font.BOLD, 14);
			g.setFont(f2);
			/**
			 * Nämä kaksi metodia pistävät näkyviin pisteiden ja tason määrät.
			 */
			g.drawString("Your score:" + String.valueOf(score), 10, 45);
			g.drawString("Your level:" + String.valueOf(level), 10, 25);

			g.drawImage(apple, appleX, appleY, this);

			for (int i = 0; i < kivet.size(); i++) {
				g.drawImage(kivi, kivet.get(i).getX(), kivet.get(i).getY(), this);
			}
            
			for (int i = 1; i < mato; i++) {
				g.drawImage(head, x[0], y[0], this);
  				if (left || right) {
					g.drawImage(body, x[i], y[i], this);
				} else {
					g.drawImage(body180, x[i], y[i], this);
				}
				if (down) {
					g.drawImage(tail, x[i + 1], y[i + 1], this);
				} else if (up) {
					g.drawImage(tailU, x[i + 1], y[i + 1], this);
				} else if (left) {
					g.drawImage(tailL, x[i + 1], y[i + 1], this);
				} else {
					g.drawImage(tailR, x[i + 1], y[i + 1], this);
				}
			}
		} else {
			g.setColor(Color.pink);
			Font f = new Font("Helvetica", Font.BOLD, 23);
			g.setFont(f);
			g.drawString("Game Over", SIZE / 2 - 75, SIZE / 2 - 20);
		}
	}

	/**
	 * Metodi määritä madon liikkumista. 
	 */
	public void move() {
		for (int i = mato; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];

		}
		if (left) {
			x[0] -= DOT_SIZE;
		}
		if (right) {
			x[0] += DOT_SIZE;
		}
		if (up) {
			y[0] -= DOT_SIZE;
		}
		if (down) {
			y[0] += DOT_SIZE;
		}
	}

	/**
	 * Metodi tarkistaa, onko madon tiellä "ihme" ja jos on, madon pituus kasvaa ja pisteiden määrä kasvaakin, kun se törmää <p>
	 * siihen. Sen jälkeen kyseinen "ihme" häviää ja kentälle ilmestyy uusi. <p>
	 * Kun mato törmää tietyyn määrään "ihmeitä", niin madon nopeus kasvaa ja tasosta tulee yksi ylemmäs.
	 */
	public void checkIhme() {
		if (x[0] == appleX && y[0] == appleY) {
			mato++;
			score++;
			switch (score) {
			case 4:
				timer.setDelay(350);
				level += 1;
				break;
			case 8:
				timer.setDelay(250);
				level += 1;
				break;
			case 12:
				timer.setDelay(150);
				level += 1;
				break;
			case 15:
				timer.setDelay(100);
				level += 1;
				break;
			}

			createIhme(); 
		}
	}

	/**
	 * Metodi tarkistaa, onko madon tiellä "suuri ei mitään", jos on, madon koko pienenee ja kentälle ilmestyy uusi osa sitä musta materiaa.
	 */
	public void checkNothing() {
		for (int i = 0; i < kivet.size(); i++) {
			if (x[0] == kivet.get(i).getX() && y[0] == kivet.get(i).getY()) {
				mato--;
				createNothing(); 
			}
		}
	}

	/**
	 * Metodi tarkistaa törmäykset itseeseen ja kentän rajoihin. Jos törmäys tapahtuu, pelia lopetetaan.
	 */
	public void checkCollisions() {
		for (int i = mato; i > 0; i--) {
			if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
				inGame = false;
			}
		}
		if (mato == 0) {
			inGame = false;
		}
		if (x[0] > SIZE) {
			inGame = false;
		}
		if (x[0] < 0) {
			inGame = false;
		}
		if (y[0] > SIZE) {
			inGame = false;
		}
		if (y[0] < 0) {
			inGame = false;
		}

		if (!inGame) {
			timer.stop();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (inGame) {
			checkIhme();
			checkNothing();
			checkCollisions();
			move();

		}
		repaint();

	}

	class FieldKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent ke) {

			super.keyPressed(ke);
			int key = ke.getKeyCode();
			if (key == KeyEvent.VK_LEFT && !right) {
				left = true;
				up = false;
				down = false;
			} else if (key == KeyEvent.VK_RIGHT && !left) {
				right = true;
				up = false;
				down = false;
			} else if (key == KeyEvent.VK_UP && !down) {
				right = false;
				left = false;
				up = true;
				down = false;
			} else if (key == KeyEvent.VK_DOWN && !up) {
				left = false;
				right = false;
				up = false;
				down = true;
			}

		}

	}

}
