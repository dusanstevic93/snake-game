package snakegame.ui;

import javax.swing.JFrame;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1522925580404092374L;

	public MainFrame(ContainerPanel container) {

		super("Snake game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(container);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}

}
