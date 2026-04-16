import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class PongPanel extends JPanel implements ActionListener, KeyListener {

	private final static Color BACKGROUND_COLOUR = Color.black;
	private final static int TIMER_DELAY = 5;
	GameState gameState = GameState.INITIALISING;
	Ball ball;
	Paddle paddle1, paddle2;
	private static final int BALL_MOVEMENT_SPEED = 2;
	
	PongPanel() {
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer (TIMER_DELAY, this);
		
			timer.start();
			
	}
	
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(getWidth(), getHeight(), Player.One);
		paddle2 = new Paddle (getWidth(), getHeight(), Player.Two);	
		
	}
	
	public void moveObject(Sprite obj) {
		obj.setXPosition(obj.getXPosition() + obj.getXVelocity(), getWidth());
		obj.setYPosition(obj.getYPosition() + obj.getYVelocity(), getHeight());
	}
	
	public void checkWallBounce() {
		if (ball.getXPosition() <= 0) {
			// Hit left side of the screen
			ball.setXVelocity(-ball.getXVelocity());
		} else if (ball.getXPosition() >= getWidth() - ball.getWidth()) {
			// Hit right side of screen
			ball.setXVelocity(-ball.getXVelocity());
		}
		if (ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() -ball.getHeight()) {
			// Hit top or bottom of the screen
			ball.setYVelocity(-ball.getYVelocity());
		}
		
	}
	
	private void update() {
		switch(gameState) {
		case INITIALISING: {
			createObjects();
			gameState = GameState.PLAYING;
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
			ball.setYVelocity(BALL_MOVEMENT_SPEED);
			break;
		}
		case PLAYING: {
			moveObject(ball);    // move ball
			checkWallBounce();   // check for wall bounce
			break;
		}
		case GAMEOVER: {
			break;
		}
		}
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2, 0, getWidth() /2, getHeight());
		g2d.dispose();
	}
	
	
	public void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.INITIALISING) {
			paintSprite(g,ball);
			paintSprite(g,paddle1);
			paintSprite(g,paddle2);
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		update();
		repaint();
		
	}
	

}
