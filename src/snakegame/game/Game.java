package snakegame.game;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Game {

	
	void moveSnake(Direction direction);
	void drawHead(Graphics2D g2D, Color color);
	void drawBody(Graphics2D g2D, Color color);
	void drawFood(Graphics2D g2D, Color color);
	boolean refresh();
	int getPoints();
	void reset();
	int getSnakeSpeed();
}
