package snakegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import snakegame.game.Direction;
import snakegame.game.Game;
import snakegame.game.Score;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 3829238706047805884L;

	private Game game;

	private Timer timer;
	
	private GameObserver observer;

	public GamePanel(Game game) {
		setFocusable(true);
		addKeyListener(this);
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent arg0) {
				requestFocusInWindow();
			}

		});
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.black);
		this.game = game;
		timer = new Timer(game.getSnakeSpeed(), this);
	}
	
	public void startGame() {
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2D = (Graphics2D) g;
		
		// paint food
		game.drawFood(graphics2D, Color.red);

		// paint head
		game.drawHead(graphics2D, Color.yellow);

		// paint body
		game.drawBody(graphics2D, Color.green);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		boolean status = game.refresh();
		timer.setDelay(game.getSnakeSpeed());
		if (status) {
			repaint();
		} else {
			processScore();
			showGameOverDialog();
		}
			

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			game.moveSnake(Direction.UP);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			game.moveSnake(Direction.DOWN);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			game.moveSnake(Direction.LEFT);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			game.moveSnake(Direction.RIGHT);
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	void setObserver(GameObserver observer) {
		this.observer = observer;
	}
	
	private void showGameOverDialog() {

		int answer = JOptionPane.showConfirmDialog(this, "Your score is: " + game.getPoints() + "\nDo you want to play again?",
				"", JOptionPane.YES_NO_OPTION);

		if (answer == 0) {
			game.reset();
		} else {
			timer.stop();
			game.reset();
			observer.showHome();
		}
	}
	
	private void processScore() {
		Score score = new Score(game.getPoints());

		if (score.isInTopFive()) {
			String name = JOptionPane.showInputDialog(this,
					"Congratulations! You have entered in top five list of players.\nPlease enter your name", "",
					JOptionPane.INFORMATION_MESSAGE);
			if (name == null)
				return;
			
			if (name.length() > 15)
				name = name.substring(0, 15);
			score.setName(name);
			score.save();
		}
	}

}
