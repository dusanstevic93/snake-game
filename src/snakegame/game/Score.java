package snakegame.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class Score implements Serializable, Comparable<Score> {

	private static final long serialVersionUID = -8833217157097707147L;

	private String name;
	private int points;

	public Score(int points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public boolean isInTopFive() {

		List<Score> scores = getScores();

		if (scores == null || scores.size() < 5 || scores.get(scores.size() - 1).points < this.points)
			return true;

		return false;
	}

	public void save() {

		List<Score> scores = getScores();

		if (scores == null) {
			scores = new ArrayList<>();
		}

		if (scores != null && scores.size() == 5) {
			scores.remove(scores.size() - 1);
		}

		scores.add(this);

		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("scores.bin"))) {

			output.writeObject(scores);

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

	}

	public static List<Score> getScores() {

		List<Score> scores = null;

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("scores.bin"))) {

			scores = (List<Score>) input.readObject();
			Collections.sort(scores);

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		}

		return scores;
	}

	@Override
	public int compareTo(Score score) {

		if (this.points > score.points)
			return -1;

		else
			return 1;
	}

	@Override
	public String toString() {
		return this.name + " " + this.points;
	}
}
