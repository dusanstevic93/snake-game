package snakegame;

import javax.swing.SwingUtilities;

import snakegame.game.GameImp;
import snakegame.ui.ContainerPanel;
import snakegame.ui.GamePanel;
import snakegame.ui.MainFrame;
import snakegame.ui.MenuPanel;
import snakegame.ui.ScorePanel;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			GamePanel gamePanel = new GamePanel(new GameImp());
			ScorePanel scorePanel = new ScorePanel();
			MenuPanel menuPanel = new MenuPanel();
			ContainerPanel containerPanel = new ContainerPanel(menuPanel, gamePanel, scorePanel);
			MainFrame mainFrame = new MainFrame(containerPanel);
		});
		
		
		

	}

}
