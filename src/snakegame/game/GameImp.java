package snakegame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GameImp implements Game {

	// dimensions of body parts and food
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;

	private Food food;
	private BodyPart head;
	private List<BodyPart> body;
	private int velX;
	private int velY;
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	private int points;
	private boolean status;
	private Queue<Direction> moves;
	private int speed;

	public GameImp() {
		init();
	}

	private void init() {
		
		food = new Food();
		body = new ArrayList<>();
		moves = new LinkedList<>();
		
		velX = 10;
		velY = 10;
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = true;
		points = 0;
		status = true;
		speed = 80;

		// initialize body with four parts
		body.add(new BodyPart(50, 50));
		body.add(new BodyPart(40, 50));
		body.add(new BodyPart(30, 50));
		body.add(new BodyPart(20, 50));

		// get head reference
		head = body.get(0);

	}
	
	@Override
	public boolean refresh() {
		checkFoodCollision();
		setCoordinates();
		moveSnakeHead();
		checkWallCollision();
		checkBodyCollision();
		return status;

	}

	@Override
	public void moveSnake(Direction direction) {
		moves.offer(direction);

	}
	
	private void moveSnakeHead() {
		
		Direction direction = moves.poll();
		
		if (direction != null) {
			switch (direction) {
			case UP:
				moveUp();
				break;
			case DOWN:
				moveDown();
				break;
			case LEFT:
				moveLeft();
				break;
			case RIGHT:
				moveRight();
				break;
			}
		}

		if (movingUp == true || movingDown == true) {
			head.setY(head.getY() + velY);

		} else if (movingLeft == true || movingRight == true) {
			head.setX(head.getX() + velX);
		}
	}

	@Override
	public void drawHead(Graphics2D g2D, Color color) {
		head.draw(g2D, color);

	}

	@Override
	public void drawBody(Graphics2D g2D, Color color) {
		for (int i = 1; i < body.size(); i++)
			body.get(i).draw(g2D, color);

	}

	@Override
	public void drawFood(Graphics2D g2D, Color color) {
		food.draw(g2D, color);

	}

	private void moveUp() {
		if (movingUp == false && movingDown == false) {
			if (velY > 0) {
				velY = -velY;
			}
			movingUp = true;
			movingLeft = false;
			movingRight = false;
		}
	}

	private void moveDown() {
		if (movingDown == false && movingUp == false) {
			if (velY < 0) {
				velY = -velY;
			}
			movingDown = true;
			movingLeft = false;
			movingRight = false;
		}
	}

	private void moveLeft() {
		if (movingRight == false && movingLeft == false) {
			if (velX > 0) {
				velX = -velX;
			}
			movingLeft = true;
			movingUp = false;
			movingDown = false;
		}
	}

	private void moveRight() {
		if (movingLeft == false && movingRight == false) {
			if (velX < 0) {
				velX = -velX;
			}
			movingRight = true;
			movingUp = false;
			movingDown = false;
		}
	}
	
	private void setCoordinates() {

		int previousX = head.getX();
		int previousY = head.getY();

		setCoordinates(1, previousX, previousY);
	}

	private void setCoordinates(int index, int previousX, int previousY) {
		if (index == body.size())
			return;
		BodyPart current = body.get(index);
		int currentX = current.getX();
		int currentY = current.getY();
		current.setX(previousX);
		current.setY(previousY);
		setCoordinates(index + 1, currentX, currentY);
	}
	
	private void checkWallCollision() {

		if (head.getX() < 0 || head.getX() == 300) {
			status = false;
		}

		if (head.getY() < 0 || head.getY() == 300) {
			status = false;
		}
	}

	private void checkFoodCollision() {

		if (head.intersects(food)) {
			food = new Food();
			body.add(new BodyPart());
			points++;
			if (points % 10 == 0 && speed >= 35)
				speed -= 10;
		}

		// prevent food spawn under snake
		for (int i = 0; i < body.size(); i++) {
			if (body.get(i).intersects(food)) {
				food = new Food();
			}

		}
	}

	private void checkBodyCollision() {
		for (int i = 1; i < body.size(); i++) {
			if (head.intersects(body.get(i))) {
				status = false;
			}
		}
	}
	
	@Override
	public int getPoints() {
		return points;
	}
	

	@Override
	public int getSnakeSpeed() {
		return speed;
	}

	@Override
	public void reset() {
		init();
		
	}

	private class BodyPart {
		private Rectangle rectangle;

		BodyPart() {
			rectangle = new Rectangle(WIDTH, HEIGHT);
		}

		BodyPart(int x, int y) {
			this();
			rectangle.setLocation(x, y);
		}

		int getX() {
			return rectangle.x;
		}

		void setX(int x) {
			rectangle.x = x;
		}

		int getY() {
			return rectangle.y;
		}

		void setY(int y) {
			rectangle.y = y;
		}

		boolean intersects(BodyPart other) {
			return this.rectangle.intersects(other.rectangle);
		}

		boolean intersects(Food food) {
			return this.rectangle.intersects(food.ellipse.getBounds2D());
		}

		void draw(Graphics2D g2D, Color color) {
			g2D.setColor(color);
			g2D.fill(rectangle);
		}
	}

	private class Food {

		private Ellipse2D ellipse;

		Food() {
			Random random = new Random();
			int x = random.nextInt(29) * 10;
			int y = random.nextInt(29) * 10;
			ellipse = new Ellipse2D.Double(x, y, WIDTH, HEIGHT);
		}

		void draw(Graphics2D g2D, Color color) {
			g2D.setColor(color);
			g2D.fill(ellipse.getBounds2D());
		}
	}

}
