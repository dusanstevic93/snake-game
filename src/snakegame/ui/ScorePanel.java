package snakegame.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import snakegame.game.Score;


public class ScorePanel extends JPanel {

	private static final long serialVersionUID = 3817728132175302101L;
	
	private List<JLabel> labels;
	private JButton back;
	private ScoreObserver observer;

	public ScorePanel() {

		setPreferredSize(new Dimension(300, 300));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(Box.createRigidArea(new Dimension(300, 50)));

		labels = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			labels.add(new JLabel());
			labels.get(i).setAlignmentX(CENTER_ALIGNMENT);
			labels.get(i).setFont(new Font("sansSerif", Font.PLAIN, 16));
			add(labels.get(i));
		}

		back = new JButton("Back");
		back.setAlignmentX(CENTER_ALIGNMENT);
		back.addActionListener(e -> observer.showHome());
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent arg0) {

				List<Score> scores = Score.getScores();

				if (scores != null) {

					for (int i = 0; i < scores.size(); i++) {
						labels.get(i).setText(i + 1 + ". " + scores.get(i).getName() + " " + scores.get(i).getPoints());
					}

				}

			}
		});

		add(Box.createGlue());
		add(back);
		add(Box.createRigidArea(new Dimension(300, 25)));

	}

	public void setObserver(ScoreObserver observer) {
		this.observer = observer;
	}

}
