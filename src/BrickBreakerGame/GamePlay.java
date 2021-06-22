package BrickBreakerGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import javax.swing.Timer;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	
	private boolean play = false;
	private int score  = 0;
	private int totalBrick = 21;
	private Timer time;
	private int delay = 8;
	private int playerx = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXDir = -1;
	private int ballYdir = -2;
	private MapGenerator map;
	
	public GamePlay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time = new Timer(delay, this);
		time.start();
		
	}
	 public void paint(Graphics g) {
		 //Background
		 g.setColor(Color.black);
		 g.fillRect(1, 1, 692, 592);
		 
		 //map draw
		 map.draw((Graphics2D)g);
		 
		 //borders
		 g.setColor(Color.yellow);
		 g.fillRect(0, 0, 3, 592);
		 g.fillRect(0, 0, 692, 3);
		 g.fillRect(691, 0, 3, 592);
		 
		 //scores
		 g.setColor(Color.white);
		 g.setFont(new Font("serif", Font.BOLD, 25));
		 g.drawString(""+score, 590, 30);
		 
		 //paddle
		 g.setColor(Color.green);
		 g.fillRect(playerx, 550, 100, 8);
		 
		 //ball
		 g.setColor(Color.yellow);
		 g.fillOval(ballposX, ballposY, 20, 20);
		 
		 if(totalBrick <= 0) {
			 play = false;
			 ballXDir = 0;
			 ballYdir = 0;
			 g.setColor(Color.red);
			 g.setFont(new Font("serif", Font.BOLD, 30));
			 g.drawString("You won: ", 260, 300);
			 
			 g.setFont(new Font("serif", Font.BOLD, 30));
			 g.drawString("Press Enter to Restart ", 230, 350);
		 }
		 
		 if(ballposY > 570) {
			 play = false;
			 ballXDir = 0;
			 ballYdir = 0;
			 g.setColor(Color.red);
			 g.setFont(new Font("serif", Font.BOLD, 30));
			 g.drawString("Game Over, Scores: ", 190, 300);
			 
			 g.setFont(new Font("serif", Font.BOLD, 30));
			 g.drawString("Press Enter to Restart ", 230, 350);
			 
		 }
		 g.dispose();
	 }
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		time.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerx, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			A:for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth+80;
						int brickY = i*map.brickLlength+50;
						int brivkWidth = map.brickWidth;
						int bricklength = map.brickLlength;
						
						Rectangle rectangle = new Rectangle(brickX, brickY, brivkWidth, bricklength);
						Rectangle ballRectangle = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRectangle = rectangle;
						
						if(ballRectangle.intersects(brickRectangle)) {
							map.setBrickValue(0, i, j);
							totalBrick--;
							score += 5;
							if(ballposX + 19 <= brickRectangle.x || ballposX+1 >= brickRectangle.x + brickRectangle.width) {
								ballXDir = -ballXDir;
							}
							else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			ballposX += ballXDir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXDir = -ballXDir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
				
			}
			if(ballposX > 670) {
				ballXDir = -ballXDir;
			}
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerx >= 600) {
				playerx = 600;
				
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerx < 10) {
				playerx = 10;
				
			}
			else {
				moveLeft();
			}
	
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXDir = -1;
				ballYdir = -2;
				playerx = 310;
				score = 0;
				totalBrick = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
			}
		}
		
	}

	private void moveLeft() {
		// TODO Auto-generated method stub
		play = true;
		playerx += 20;
		
	}
	private void moveRight() {

		play = true;
		playerx -= 20;
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
