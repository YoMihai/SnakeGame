import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

	static final int LATIME = 600;
	static final int INALTIME = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (LATIME*INALTIME)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyparts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char DIRECTION = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(LATIME,INALTIME));
		
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
		
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		if(running)
		{g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		for(int i=0;i<bodyparts;i++)
			if(i == 0)
			{
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45,180,0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				
			}
		g.setColor(Color.red);
		g.setFont(new Font("Times New Roman", Font.BOLD,30));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score:"+applesEaten, (LATIME - metrics.stringWidth("Score:"+applesEaten))/2, INALTIME/15);
		
		
		
		}
		else {
			GameOver(g);
		}
	}
	
	public void move() {
		
		
		for(int i=bodyparts;i>0;i--) {
			
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
			
		switch(DIRECTION)
		{
			case 'L': x[0] = x[0] - UNIT_SIZE; break;
			case 'R': x[0] = x[0] + UNIT_SIZE; break;
			case 'U': y[0] = y[0] - UNIT_SIZE; break;
			case 'D': y[0] = y[0] + UNIT_SIZE; break;
			
			
		}
		
	}
	
	public void GameOver(Graphics g) {
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (LATIME - metrics.stringWidth("Game Over"))/2, INALTIME/2);
		
		
		g.setColor(Color.red);
		g.setFont(new Font("Times New Roman", Font.BOLD,50));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score:"+applesEaten, (LATIME - metrics2.stringWidth("Score:"+applesEaten))/2, INALTIME/4);
		
	}
	public void newApple() {
		
		appleX = random.nextInt((int)(LATIME/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(INALTIME/UNIT_SIZE))*UNIT_SIZE;
		
		
		
		
		
	}
	
	public void checkApple() {
		
		if(x[0] == appleX && y[0] == appleY) {
			applesEaten++;
			newApple();
			bodyparts++;
			
		}
			
		
	}
	
	public void checkCollision() {
		
		for(int i=bodyparts;i>0;i--)
		{
			if((x[0] == x[i]) && y[0] == y[i]) {
				running = false;
			}
		}
		
		if(x[0] > LATIME) {
			running = false;
		}
		
		if(y[0] > INALTIME) {
			running = false;
		}
		
		if(x[0] < 0){
			running = false;
		}
		
		if(y[0] < 0) {
			running = false;
		}
		
		if(running == false) {
			timer.stop();
		}
			
		
		
			
			
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running)
		{
			move();
			checkApple();
			checkCollision();
			
		}
		repaint();
	}
	
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		
		public void keyPressed( KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(DIRECTION != 'R')
					DIRECTION = 'L';
				break;
			case KeyEvent.VK_RIGHT:
				if(DIRECTION != 'L')
					DIRECTION = 'R';
				break;
			case KeyEvent.VK_UP:
				if(DIRECTION != 'D')
					DIRECTION = 'U';
				break;
			case KeyEvent.VK_DOWN:
				if(DIRECTION != 'U')
					DIRECTION = 'D';
				break;
			
			}
			
		}
	}

}
