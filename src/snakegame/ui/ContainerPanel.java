package snakegame.ui;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class ContainerPanel extends JPanel {

	private static final long serialVersionUID = 8457106690034003637L;
	
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private ScorePanel scorePanel;
	private CardLayout layout;

	public ContainerPanel(MenuPanel menu, GamePanel game, ScorePanel score) {

		menuPanel = menu;
		menuPanel.setObserver(button -> {
			if (button.equals("New Game")) {
				layout.show(this, "game");
				gamePanel.startGame();
			}
			else if (button.equals("High Score"))
				layout.show(this, "score");
		});
		gamePanel = game;
		gamePanel.setObserver(() -> layout.show(this, "menu"));
		scorePanel = new ScorePanel();
		scorePanel.setObserver(() -> layout.show(this, "menu"));
		layout = new CardLayout();
		setLayout(layout);
		add(menuPanel, "menu");
		add(gamePanel, "game");
		add(scorePanel, "score");
		layout.show(this, "menu");
	}
}
