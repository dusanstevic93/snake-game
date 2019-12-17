package snakegame.ui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = -6763263550217169177L;
	
	private JButton newGame;
	private JButton highScore;
	private JButton exit;
	private MenuObserver observer;

	public MenuPanel() {

		newGame = new JButton("New Game");
		newGame.setAlignmentX(CENTER_ALIGNMENT);
		newGame.addActionListener(e -> observer.update(newGame.getText()));

		highScore = new JButton("High Score");
		highScore.setAlignmentX(CENTER_ALIGNMENT);
		highScore.addActionListener(e -> observer.update(highScore.getText()));

		exit = new JButton("Exit");
		exit.setAlignmentX(CENTER_ALIGNMENT);
		exit.addActionListener(e -> System.exit(0));

		setPreferredSize(new Dimension(300, 300));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(300, 95)));
		add(newGame);
		add(Box.createRigidArea(new Dimension(300, 30)));
		add(highScore);
		add(Box.createRigidArea(new Dimension(300, 50)));
		add(exit);
	}

	public void setObserver(MenuObserver observer) {
		this.observer = observer;
	}

}
